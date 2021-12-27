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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

@DisplayName("CurrencyRegistry Tests")
public final class CurrencyRegistryTests {

    @Test
    @DisplayName("CurrencyRegistry::overwrite correctly overwrites with valid collection")
    public void overwriteOverwritesOnValidCollection() {
        Set<Currency> currencies = Set.of(
                new Currency("EUR", "Euro"),
                new Currency("CHF", "Swiss Franc"));
        CurrencyRegistry.INSTANCE.update(currencies);

        Assertions.assertEquals(currencies, CurrencyRegistry.INSTANCE.getCurrencies());
    }

    @Test
    @DisplayName("CurrencyRegistry::overwrite correctly overwrites with valid non-empty array")
    public void overwriteOverwritesOnValidNonEmptyArray() {
        Currency japaneseYen = new Currency("JPY", "Japanese Yen");
        CurrencyRegistry.INSTANCE.update(japaneseYen);

        Set<Currency> expected = Set.of(japaneseYen);
        Set<Currency> actual = CurrencyRegistry.INSTANCE.getCurrencies();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("CurrencyRegistry::overwrite correctly overwrites with valid empty array")
    public void overwriteOverwritesOnValidEmptyArray() {
        CurrencyRegistry.INSTANCE.update();

        Set<Currency> expected = Set.of();
        Set<Currency> actual = CurrencyRegistry.INSTANCE.getCurrencies();

        Assertions.assertEquals(expected, actual);
    }
}
