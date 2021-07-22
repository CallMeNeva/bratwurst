package com.altmax.rates.service.response;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

public final class ExchangeRatesResponseBodyDeserializer implements JsonDeserializer<ExchangeRatesResponseBody> {

    @Override
    public ExchangeRatesResponseBody deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject root = json.getAsJsonObject();
            Instant timestamp = Instant.ofEpochSecond(root.get("timestamp").getAsLong());
            String baseCurrencyCode = root.get("base").getAsString();
            /* Interpret the property "rates" as a JSON object --> convert to an entry set stream --> collect as Map<String, Double> */
            Map<String, Double> rates = root.get("rates").getAsJsonObject().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().getAsDouble()));
            return new ExchangeRatesResponseBody(timestamp, baseCurrencyCode, rates);
        } catch (IllegalStateException | ClassCastException | DateTimeException e) {
            throw new JsonParseException(e);
        }
    }
}
