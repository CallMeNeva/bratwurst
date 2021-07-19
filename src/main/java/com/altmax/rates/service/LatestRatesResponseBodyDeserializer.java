package com.altmax.rates.service;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

public final class LatestRatesResponseBodyDeserializer implements JsonDeserializer<LatestRatesResponseBody> {

    @NotNull
    @Override
    public LatestRatesResponseBody deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject root = json.getAsJsonObject();
            Instant timestamp = Instant.ofEpochSecond(root.get("timestamp").getAsLong());
            String baseCurrencyCode = root.get("base").getAsString();
            Map<String, Double> rates = root.get("rates").getAsJsonObject().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().getAsDouble()));
            return new LatestRatesResponseBody(timestamp, baseCurrencyCode, rates);
        } catch (IllegalArgumentException | DateTimeException | ClassCastException e) {
            throw new JsonParseException(e);
        }
    }
}
