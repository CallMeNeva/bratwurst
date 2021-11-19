package io.github.altoukhovmax.frankfurterdesktop.model;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * Models an exchange rate. Each one is represented by:
 *     - a base currency;
 *     - a target currency;
 *     - the actual exchange rate value;
 *     - a publication date.
 * None of the values may be null. This class is immutable.
 */
public record ExchangeRate(@NotNull Currency baseCurrency,
                           @NotNull Currency targetCurrency,
                           @NotNull BigDecimal value,
                           @NotNull LocalDate publicationDate) {
}
