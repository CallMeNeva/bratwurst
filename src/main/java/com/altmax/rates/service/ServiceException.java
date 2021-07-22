package com.altmax.rates.service;

import org.jetbrains.annotations.NotNull;

public final class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(@NotNull String message) {
        super(message);
    }

    public ServiceException(@NotNull Throwable cause) {
        super(cause);
    }
}
