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

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

@DisplayName("Currency Tests")
public final class CurrencyTests {

    @Test
    @DisplayName("Currency::ofEntry correctly constructs from a valid Map.Entry")
    public void ofEntryConstructsFromValidEntry() {
        Map.Entry<String, String> entry = new AbstractMap.SimpleImmutableEntry<>("USD", "United States Dollar");

        Currency expected = new Currency("USD", "United States Dollar");
        Currency actual = Currency.ofEntry(entry);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Currency::ofMap correctly constructs from a valid Map")
    public void ofMapConstructsFromValidMap() {
        Map<String, String> map = Map.of(
                "EUR", "Euro",
                "CHF", "Swiss Franc"
        );

        Set<Currency> expected = Set.of(
                new Currency("EUR", "Euro"),
                new Currency("CHF", "Swiss Franc")
        );
        Set<Currency> actual = Currency.ofMap(map);

        Assertions.assertEquals(expected, actual);
    }
}
