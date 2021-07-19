package com.altmax.rates.model;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class ExchangeRate {

    private final @NotNull String baseCurrencyCode;
    private final @NotNull String targetCurrencyCode;
    private final double factor;
    private final @NotNull Instant timestamp;
    private final @NotNull String formattedTimestamp;

    public ExchangeRate(@NotNull String baseCurrencyCode,
                        @NotNull String targetCurrencyCode,
                        double factor,
                        @NotNull Instant timestamp) {
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

    @NotNull
    @Override
    public String toString() {
        return String.format("[1x%s == %fx%s (%s)]",
                             this.baseCurrencyCode,
                             this.factor,
                             this.targetCurrencyCode,
                             this.getFormattedTimestamp());
    }
}
