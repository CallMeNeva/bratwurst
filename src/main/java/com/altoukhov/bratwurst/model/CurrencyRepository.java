// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.model;

import com.altoukhov.bratwurst.util.Arguments;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CurrencyRepository {

    private final Set<Currency> currencies;

    public CurrencyRepository() {
        currencies = new HashSet<>();
    }

    public Set<Currency> getCurrencies() {
        return Collections.unmodifiableSet(currencies);
    }

    public Currency find(String currencyCode) throws CurrencyNotFoundException {
        Arguments.checkNull(currencyCode, "currencyCode");
        return currencies.stream()
                .filter(currency -> currencyCode.equals(currency.code()))
                .findAny()
                .orElseThrow(() -> new CurrencyNotFoundException("No currency with code \"" + currencyCode + "\" was found"));
    }

    public void update(Set<Currency> newCurrencies) {
        Arguments.checkNull(newCurrencies, "newCurrencies");
        // FIXME: Invariant: check if contains null elements
        // TODO: Profile memory usage: full overwrite vs only adding/removing differences (GC-related stuff)
        currencies.clear();
        currencies.addAll(newCurrencies);
    }
}
