/*
 * Copyright 2021 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.altoukhovmax.frankfurterdesktop.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

/**
 * Models an immutable exchange rate. Each one is represented by:
 * <ul>
 *     <li>a base currency;</li>
 *     <li>a target currency;</li>
 *     <li>the actual exchange rate value (i.e. how much 1 unit of the base currency is worth in the target currency);</li>
 *     <li>a publication date.</li>
 * </ul>
 *
 * @see Currency
 * @see CurrencyRegistry
 */
public record ExchangeRate(Currency base, Currency target, BigDecimal value, LocalDate date) {

    private static final Function<String, IllegalArgumentException> CURRENCY_NOT_FOUND_EXCEPTION_GENERATOR = /* Used in ::of */
            currencyCode -> new IllegalArgumentException("No currency with code '" + currencyCode + "' was found");

    /**
     * Constructs an {@code ExchangeRate} instance with the given properties after null-checking them first.
     *
     * @param base   the exchange rate's base currency (not null)
     * @param target the exchange rate's target currency (not null)
     * @param value  the exchange rate's value (not null)
     * @param date   the exchange rate's publication date (not null)
     * @throws NullPointerException if any of the provided values is null
     */
    public ExchangeRate {
        Objects.requireNonNull(base, "Provided base is null");
        Objects.requireNonNull(target, "Provided target is null");
        Objects.requireNonNull(value, "Provided value is null");
        Objects.requireNonNull(date, "Provided date is null");
    }

    /**
     * Constructs an {@code ExchangeRate} instance with the currencies pulled from the {@link CurrencyRegistry} by their ISO-4217 codes.
     *
     * @param baseCode   the ISO-4217 code of the exchange rate's base currency
     * @param targetCode the ISO-4217 code of the exchange rate's target currency
     * @param value      the exchange rate's value (not null)
     * @param date       the exchange rate's publication date (not null)
     * @return a new {@code ExchangeRate} instance (not null)
     * @throws NullPointerException     if {@code value} or {@code date} is null
     * @throws IllegalArgumentException if failed to find a currency in the registry with the given codes
     */
    public static ExchangeRate of(String baseCode, String targetCode, BigDecimal value, LocalDate date) {
        CurrencyRegistry registry = CurrencyRegistry.INSTANCE;
        Currency base = registry.findByCode(baseCode).orElseThrow(() -> CURRENCY_NOT_FOUND_EXCEPTION_GENERATOR.apply(baseCode));
        Currency target = registry.findByCode(targetCode).orElseThrow(() -> CURRENCY_NOT_FOUND_EXCEPTION_GENERATOR.apply(targetCode));

        return new ExchangeRate(base, target, value, date);
    }

    /**
     * Converts a specified amount of the base currency to the target currency using this exchange rate.
     *
     * @param amount the amount of the exchange rate's target currency to convert (not null)
     * @return the resulting amount of the exchange rate's base currency (not null)
     * @throws NullPointerException if {@code amount} is null
     */
    public BigDecimal convert(BigDecimal amount) {
        return Objects.requireNonNull(amount, "Provided amount is null").multiply(value);
    }
}
