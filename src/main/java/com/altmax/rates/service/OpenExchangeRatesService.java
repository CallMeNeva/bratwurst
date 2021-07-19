package com.altmax.rates.service;

import com.altmax.rates.model.ExchangeRate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class OpenExchangeRatesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenExchangeRatesService.class);
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson JSON_PARSER = new GsonBuilder()
            .registerTypeAdapter(LatestRatesResponseBody.class, new LatestRatesResponseBodyDeserializer())
            .registerTypeAdapter(ErrorResponseBody.class, new ErrorResponseBodyDeserializer()).create();

    public OpenExchangeRatesService() {
    }

    /* Why bother writing good code when you can just do shit like this? */
    @NotNull
    public List<ExchangeRate> fetchLatestRates(@NotNull String appId, @NotNull String baseCurrencyCode)
            throws IOException, InterruptedException, JsonParseException, ServiceException {
        Objects.requireNonNull(appId, "Supplied App ID is null");
        Objects.requireNonNull(baseCurrencyCode, "Supplied base currency code is null");

        String url = String.format("https://openexchangerates.org/api/latest.json?app_id=%s&base=%s&prettyprint=0", appId, baseCurrencyCode);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().timeout(Duration.ofSeconds(15)).build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        LOGGER.info(request + " returned " + response.statusCode());

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            LatestRatesResponseBody responseBody = JSON_PARSER.fromJson(response.body(), LatestRatesResponseBody.class);
            return responseBody.getRates().entrySet().stream()
                    .map((e) -> new ExchangeRate(responseBody.getBaseCurrencyCode(), e.getKey(), e.getValue(), responseBody.getTimestamp()))
                    .collect(Collectors.toList());
        }

        ErrorResponseBody errorResponseBody = JSON_PARSER.fromJson(response.body(), ErrorResponseBody.class);
        throw new ServiceException(errorResponseBody);
    }
}
