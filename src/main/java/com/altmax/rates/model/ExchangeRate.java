package com.altmax.rates.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class ExchangeRate {

    private final String baseCurrencyCode;
    private final String targetCurrencyCode;
    private final double factor;
    private final LocalDateTime timestamp;

    public ExchangeRate(String baseCurrencyCode, String targetCurrencyCode, double factor, LocalDateTime timestamp) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.factor = factor;
        this.timestamp = timestamp;
    }

    public String getBaseCurrencyCode() {
        return this.baseCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return this.targetCurrencyCode;
    }

    public double getFactor() {
        return this.factor;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getFormattedTimestamp() {
        return this.timestamp.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    @Override
    public String toString() {
        return String.format("[1x%s == %fx%s (%s)]", this.baseCurrencyCode, this.factor, this.targetCurrencyCode, this.timestamp);
    }
}
