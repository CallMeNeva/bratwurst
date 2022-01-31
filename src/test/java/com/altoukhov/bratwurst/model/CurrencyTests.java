// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

@DisplayName("Currency Tests")
public final class CurrencyTests {

    @Test
    @DisplayName("Currency::ofEntry constructs a Currency given a non-null Map.Entry")
    public void constructsGivenNonNullEntry() {
        Map.Entry<String, String> entry = new AbstractMap.SimpleImmutableEntry<>("USD", "United States Dollar");

        Currency expected = new Currency("USD", "United States Dollar");
        Currency actual = Currency.ofEntry(entry);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Currency::ofMap constructs a Set of Currency given a non-null Map")
    public void constructsGivenNonNullMap() {
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
