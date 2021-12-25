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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A global, centralized repository of currencies (singleton).
 *
 * @see Currency
 * @see ExchangeRate
 */
public enum CurrencyRegistry {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRegistry.class);

    private final Set<Currency> currencies; /* FIXME: Is thread-safety needed? */

    CurrencyRegistry() {
        currencies = new HashSet<>();
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    /**
     * Finds the currency identified by the specified ISO-4217 code within this registry.
     *
     * @param code an ISO-4217 code
     * @return an {@link Optional} with a currency if it was found and an empty one otherwise
     */
    public Optional<Currency> findByCode(String code) {
        return currencies.stream()
                .filter(currency -> Objects.equals(code, currency.code()))
                .findAny();
    }

    /**
     * Overwrites this registry's contents with the ones in the provided collection.
     *
     * @param currencies a collection of currencies
     * @throws NullPointerException if {@code currencies} is null
     */
    public void overwrite(Collection<Currency> currencies) {
        Objects.requireNonNull(currencies, "Provided collection of currencies is null");

        this.currencies.clear();
        this.currencies.addAll(currencies);

        LOGGER.info("Contents have been overwritten");
    }

    /**
     * Overwrites this registry's contents with the provided currencies.
     *
     * @param currencies the registry's new contents
     * @throws NullPointerException if {@code currencies} is null
     */
    public void overwrite(Currency... currencies) {
        overwrite(Arrays.asList(currencies));
    }
}
