// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.model;

import com.altoukhov.bratwurst.util.Arguments;

import java.util.Map;

public record Sum(Currency currency, double amount) {

    public Sum {
        Arguments.checkNull(currency, "currency");
    }

    public static Sum of(String currencyCode, double amount, CurrencyRepository currencyRepository) throws CurrencyNotFoundException {
        Arguments.checkNull(currencyRepository, "currencyRepository");
        Currency currency = currencyRepository.find(currencyCode);
        return new Sum(currency, amount);
    }

    public static Sum of(Map.Entry<String, Double> entry, CurrencyRepository currencyRepository) throws CurrencyNotFoundException {
        Arguments.checkNull(entry, "entry");
        return Sum.of(entry.getKey(), entry.getValue(), currencyRepository);
    }
}
