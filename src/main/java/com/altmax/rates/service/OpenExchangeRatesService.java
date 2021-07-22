package com.altmax.rates.service;

import com.altmax.rates.model.ExchangeRate;
import com.altmax.rates.service.request.ExchangeRatesRequestURIFactory;
import com.altmax.rates.service.response.ErrorResponseBody;
import com.altmax.rates.service.response.ErrorResponseBodyDeserializer;
import com.altmax.rates.service.response.ExchangeRatesResponseBody;
import com.altmax.rates.service.response.ExchangeRatesResponseBodyDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public final class OpenExchangeRatesService implements ExchangeRatesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenExchangeRatesService.class);
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson JSON_PARSER = new GsonBuilder()
            .registerTypeAdapter(ExchangeRatesResponseBody.class, new ExchangeRatesResponseBodyDeserializer())
            .registerTypeAdapter(ErrorResponseBody.class, new ErrorResponseBodyDeserializer()).create();

    public OpenExchangeRatesService() {
    }

    @Override
    @NotNull
    public List<ExchangeRate> fetchRates(@NotNull String appId,
                                         @NotNull String baseCurrencyCode,
                                         boolean includeExtras,
                                         @NotNull String... targetCurrencyCodes) throws ServiceException {
        return fetchRates(ExchangeRatesRequestURIFactory.create(appId, baseCurrencyCode, includeExtras, targetCurrencyCodes));
    }

    @Override
    @NotNull
    public List<ExchangeRate> fetchRates(@NotNull String appId,
                                         @NotNull String baseCurrencyCode,
                                         @NotNull LocalDate date,
                                         boolean includeExtras,
                                         @NotNull String... targetCurrencyCodes) throws ServiceException {
        return fetchRates(ExchangeRatesRequestURIFactory.create(appId, baseCurrencyCode, date, includeExtras, targetCurrencyCodes));
    }

    @NotNull
    private List<ExchangeRate> fetchRates(URI uri) throws ServiceException {
        try {
            HttpResponse<String> response = sendRequest(uri);
            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                ExchangeRatesResponseBody responseBody = JSON_PARSER.fromJson(response.body(), ExchangeRatesResponseBody.class);
                return responseBody.getRates().entrySet().stream().map(
                        (e) -> new ExchangeRate(responseBody.getBaseCurrencyCode(), e.getKey(), e.getValue(), responseBody.getTimestamp()))
                        .collect(Collectors.toList());
            }
            ErrorResponseBody error = JSON_PARSER.fromJson(response.body(), ErrorResponseBody.class);
            LOGGER.error("Service returned an error of type \"" + error.getMessage() + "\"");
            throw new ServiceException(error.getDescription());
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Failed to send request: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @NotNull
    private static HttpResponse<String> sendRequest(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(uri).GET().timeout(Duration.ofSeconds(15)).build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        LOGGER.info("Request returned " + response.statusCode());
        return response;
    }
}
