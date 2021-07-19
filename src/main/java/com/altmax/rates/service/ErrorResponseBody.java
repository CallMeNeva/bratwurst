package com.altmax.rates.service;

import org.jetbrains.annotations.NotNull;

public final class ErrorResponseBody {

    private final short status;
    private final @NotNull String message;
    private final @NotNull String description;

    public ErrorResponseBody(short status, @NotNull String message, @NotNull String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }

    public short getStatus() {
        return this.status;
    }

    @NotNull
    public String getMessage() {
        return this.message;
    }

    @NotNull
    public String getDescription() {
        return this.description;
    }
}
