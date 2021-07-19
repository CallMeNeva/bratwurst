package com.altmax.rates.service;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Map;

public final class LatestRatesResponseBody {

    private final @NotNull Instant timestamp;
    private final @NotNull String baseCurrencyCode;
    private final @NotNull Map<String, Double> rates;

    public LatestRatesResponseBody(@NotNull Instant timestamp,
                                   @NotNull String baseCurrencyCode,
                                   @NotNull Map<String, Double> rates) {
        this.timestamp = timestamp;
        this.baseCurrencyCode = baseCurrencyCode;
        this.rates = rates;
    }

    @NotNull
    public Instant getTimestamp() {
        return this.timestamp;
    }

    @NotNull
    public String getBaseCurrencyCode() {
        return this.baseCurrencyCode;
    }

    @NotNull
    public Map<String, Double> getRates() {
        return this.rates;
    }
}
