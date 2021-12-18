package io.github.altoukhovmax.frankfurterdesktop.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/*
 * Models an exchange rate. Each one is represented by:
 *     - a base currency;
 *     - a target currency;
 *     - the actual exchange rate value (i.e. how much 1 unit of the base currency is worth in the target currency);
 *     - a publication date.
 *
 * None of the fields may be null. This class is immutable.
 */
public record ExchangeRate(Currency base, Currency target, BigDecimal value, LocalDate publicationDate) {

    public ExchangeRate {
        Objects.requireNonNull(base);
        Objects.requireNonNull(target);
        Objects.requireNonNull(value);
        Objects.requireNonNull(publicationDate);
    }

    public ExchangeRate(String baseCode,
                        String targetCode,
                        BigDecimal value,
                        LocalDate publicationDate) throws IllegalArgumentException {
        this(Currency.ofCode(baseCode), Currency.ofCode(targetCode), value, publicationDate);
    }

    /*
     * Converts a specified amount of the base currency to the target currency using this exchange rate.
     */
    public BigDecimal convert(BigDecimal amount) {
        return Objects.requireNonNull(amount).multiply(value);
    }
}
