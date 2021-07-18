package com.altmax.rates.api;

public final class ErrorResponseBody {

    private final short status;
    private final String message;
    private final String description;

    public ErrorResponseBody(short status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }

    public short getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDescription() {
        return this.description;
    }
}
