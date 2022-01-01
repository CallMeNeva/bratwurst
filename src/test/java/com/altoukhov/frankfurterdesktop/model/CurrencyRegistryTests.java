/*
 * Copyright 2021, 2022 Maxim Altoukhov
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

package com.altoukhov.frankfurterdesktop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

@DisplayName("CurrencyRegistry Tests")
public final class CurrencyRegistryTests {

    @Test
    @DisplayName("CurrencyRegistry::find finds a Currency given a valid ISO code")
    public void findsGivenValidCode() {
        Currency expected = new Currency("JPY", "Japanese Yen");
        CurrencyRegistry registry = new CurrencyRegistry(expected);
        Currency actual = registry.find("JPY");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("CurrencyRegistry::find throws CurrencyNotFoundException given an invalid ISO code")
    public void throwsGivenInvalidCode() {
        CurrencyRegistry registry = new CurrencyRegistry();

        Assertions.assertThrows(CurrencyNotFoundException.class, () -> registry.find("JPY"));
    }

    @Test
    @DisplayName("CurrencyRegistry::update updates contents given a valid Collection")
    public void updatesGivenValidCollection() {
        CurrencyRegistry registry = new CurrencyRegistry();

        Set<Currency> currencies = Set.of(
                new Currency("EUR", "Euro"),
                new Currency("CHF", "Swiss Franc")
        );
        registry.update(currencies);

        Assertions.assertEquals(currencies, registry.getCurrencies());
    }
}
