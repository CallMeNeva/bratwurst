package com.altmax.rates.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class ExchangeRatesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRatesService.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(15);
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson JSON_PARSER = new GsonBuilder()
            .registerTypeAdapter(LatestRatesResponseBody.class, new LatestRatesResponseBodyDeserializer())
            .registerTypeAdapter(ErrorResponseBody.class, new ErrorResponseBodyDeserializer()).create();

    public static LatestRatesResponseBody getLatest(String appId, String currencyCode)
            throws IllegalArgumentException, IOException, InterruptedException, JsonParseException, APIException {
        if (appId == null || currencyCode == null) {
            throw new IllegalArgumentException("Supplied App ID or currency code is null");
        }
        String url = String.format("https://openexchangerates.org/api/latest.json?app_id=%s&base=%s", appId, currencyCode);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().timeout(TIMEOUT_DURATION).build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        LOGGER.info(request + " returned " + response.statusCode());
        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            return JSON_PARSER.fromJson(response.body(), LatestRatesResponseBody.class);
        }
        ErrorResponseBody errorResponseBody = JSON_PARSER.fromJson(response.body(), ErrorResponseBody.class);
        throw new APIException(errorResponseBody);
    }
}
