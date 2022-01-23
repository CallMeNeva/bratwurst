// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

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
