package io.github.altoukhovmax.rates.domain.entity;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record ExchangeRate(@NotNull String baseCurrencyCode,
                           @NotNull String targetCurrencyCode,
                           double factor,
                           @NotNull LocalDate publicationDate) {
}
