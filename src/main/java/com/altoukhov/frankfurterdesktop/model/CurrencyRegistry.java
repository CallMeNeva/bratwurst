// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A centralized registry of currencies.
 * <p>
 * Since {@code Frankfurter} (as well as many other exchange rates services) does not provide full information about the currencies
 * specified in provided exchange rates, it's a bit difficult (and not very convenient) to work with the service in a stateless manner. This
 * means that the application needs to have a centralized, globally-accessible set of currencies that can be read from. However, simply
 * enumerating all the currencies statically is not an option because that centralized set may need to be updated dynamically at runtime
 * (unlikely, but is by no means an exceptional case). This is where {@code CurrencyRegistry} comes into play, essentially providing an
 * extremely thin wrapper around a {@link Set} with a global-access instance.
 *
 * @implNote This class is currently (probably?) not thread-safe. The application's upper layers currently do read/write operations on the
 * global registry sequentially, so there should be no need to bother with thread-safety unless that changes.
 * @see Currency
 * @see Exchange
 */
public final class CurrencyRegistry {

    /**
     * The global registry instance. Other parts of the application should treat this class as a singleton and pretty much always use only
     * the global instance. {@code CurrencyRegistry} is not made strictly a singleton mostly for testing purposes, although the ability to
     * construct a separate registry might come in handy in production code as well.
     */
    public static final CurrencyRegistry GLOBAL = new CurrencyRegistry();

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRegistry.class);

    private final Set<Currency> currencies;

    public CurrencyRegistry(Currency... currencies) {
        this.currencies = new HashSet<>();
        Collections.addAll(this.currencies, currencies);
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    /**
     * Finds the currency identified by the specified ISO-4217 code within this registry.
     *
     * @param code an ISO-4217 code
     * @return a {@code Currency}
     * @throws CurrencyNotFoundException if failed to find a currency identified by the provided code
     */
    public Currency find(String code) throws CurrencyNotFoundException {
        return currencies.stream()
                .filter(currency -> Objects.equals(code, currency.code()))
                .findAny()
                .orElseThrow(() -> new CurrencyNotFoundException("No currency with code '" + code + "' was found"));
    }

    /**
     * Updates (overwrites) this registry's contents with the ones in the provided collection.
     *
     * @param currencies a collection of currencies
     * @throws NullPointerException if {@code currencies} is null
     */
    public void update(Collection<Currency> currencies) {
        /* Redundant null-check (Set::addAll already does one) as to not clear without writing as well */
        Objects.requireNonNull(currencies, "Provided collection of currencies is null");

        this.currencies.clear();
        this.currencies.addAll(currencies);

        LOGGER.info("Updated contents");
    }

    /**
     * Updates (overwrites) this registry's contents with the provided currencies.
     *
     * @param currencies the registry's new contents
     */
    public void update(Currency... currencies) {
        update(Arrays.asList(currencies));
    }
}
