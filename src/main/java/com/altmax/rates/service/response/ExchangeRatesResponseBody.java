package com.altmax.rates.service.response;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Map;

public final class ExchangeRatesResponseBody {

    private final Instant timestamp;
    private final String baseCurrencyCode;
    private final Map<String, Double> rates;

    public ExchangeRatesResponseBody(@NotNull Instant timestamp, @NotNull String baseCurrencyCode, @NotNull Map<String, Double> rates) {
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
