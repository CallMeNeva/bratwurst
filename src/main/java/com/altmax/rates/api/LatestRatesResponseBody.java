package com.altmax.rates.api;

import java.time.LocalDateTime;
import java.util.Map;

public final class LatestRatesResponseBody {

    private final LocalDateTime timestamp;
    private final String baseCurrencyCode;
    private final Map<String, Double> rates;

    public LatestRatesResponseBody(LocalDateTime timestamp, String baseCurrencyCode, Map<String, Double> rates) {
        this.timestamp = timestamp;
        this.baseCurrencyCode = baseCurrencyCode;
        this.rates = rates;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getBaseCurrencyCode() {
        return this.baseCurrencyCode;
    }

    public Map<String, Double> getRates() {
        return this.rates;
    }
}
