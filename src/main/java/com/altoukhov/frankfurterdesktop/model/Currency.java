// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Models an immutable currency. Each one is represented by:
 * <ul>
 *     <li>an ISO-4217 code, e.g. "USD";</li>
 *     <li>an official display name, e.g. "United Stated Dollar".</li>
 * </ul>
 * <p>
 * Keep in mind that this class is nothing more than just a dumb data object with zero validation or curation performed (see
 * {@code CurrencyRegistry} for the latter).
 *
 * @see CurrencyRegistry
 * @see Exchange
 */
public record Currency(String code, String displayName) {

    /**
     * Convenience factory method to construct a {@code Currency} from a {@link Map.Entry}.
     *
     * @param entry a map entry (not null)
     * @return a new instance of {@code Currency} (not null)
     * @throws NullPointerException if {@code entry} is null
     */
    public static Currency ofEntry(Map.Entry<String, String> entry) {
        Objects.requireNonNull(entry, "Provided entry is null");
        return new Currency(entry.getKey(), entry.getValue());
    }

    /**
     * Convenience factory method to construct a {@link Set} of {@code Currency} from a {@link Map}.
     *
     * @param map a map (not null)
     * @return a new set of currencies (not null)
     * @throws NullPointerException if {@code map} is null
     */
    public static Set<Currency> ofMap(Map<String, String> map) {
        Objects.requireNonNull(map, "Provided map is null");
        return map.entrySet()
                .stream()
                .map(Currency::ofEntry)
                .collect(Collectors.toSet());
    }
}
