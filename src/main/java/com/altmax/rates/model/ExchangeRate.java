package com.altmax.rates.model;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class ExchangeRate {

    private final String baseCurrencyCode;
    private final String targetCurrencyCode;
    private final double factor;
    private final Instant timestamp;
    private final String formattedTimestamp;

    public ExchangeRate(@NotNull String baseCurrencyCode, @NotNull String targetCurrencyCode, double factor, @NotNull Instant timestamp) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.factor = factor;
        this.timestamp = timestamp;
        this.formattedTimestamp = LocalDateTime.ofInstant(this.timestamp, ZoneId.systemDefault())
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    @NotNull
    public String getBaseCurrencyCode() {
        return this.baseCurrencyCode;
    }

    @NotNull
    public String getTargetCurrencyCode() {
        return this.targetCurrencyCode;
    }

    public double getFactor() {
        return this.factor;
    }

    @NotNull
    public Instant getTimestamp() {
        return this.timestamp;
    }

    @NotNull
    public String getFormattedTimestamp() {
        return this.formattedTimestamp;
    }

    @Override
    public String toString() {
        return "[1x " + this.baseCurrencyCode + " == " + this.factor + "x " + this.targetCurrencyCode + " (" + this.getFormattedTimestamp() + ")]";
    }
}
