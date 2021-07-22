package com.altmax.rates.service.response;

import org.jetbrains.annotations.NotNull;

public final class ErrorResponseBody {

    private final String message;
    private final String description;

    public ErrorResponseBody(@NotNull String message, @NotNull String description) {
        this.message = message;
        this.description = description;
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
