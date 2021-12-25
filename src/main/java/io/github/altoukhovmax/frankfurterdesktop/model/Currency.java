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
 * @see ExchangeRate
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
