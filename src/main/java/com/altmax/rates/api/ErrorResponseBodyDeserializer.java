package com.altmax.rates.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public final class ErrorResponseBodyDeserializer implements JsonDeserializer<ErrorResponseBody> {

    @Override
    public ErrorResponseBody deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject root = json.getAsJsonObject();
            short status = root.get("status").getAsShort();
            String message = root.get("message").getAsString();
            String description = root.get("description").getAsString();
            return new ErrorResponseBody(status, message, description);
        } catch (IllegalArgumentException | ClassCastException e) {
            throw new JsonParseException(e);
        }
    }
}
