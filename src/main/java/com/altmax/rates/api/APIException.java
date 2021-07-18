package com.altmax.rates.api;

public final class APIException extends RuntimeException {

    public APIException(String message) {
        super(message);
    }

    public APIException(ErrorResponseBody responseBody) {
        this(responseBody.getDescription());
    }
}
