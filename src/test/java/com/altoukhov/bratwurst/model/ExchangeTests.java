// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

@DisplayName("Exchange Tests")
public final class ExchangeTests {

    @Test
    @DisplayName("Exchange::fromRegistry constructs an Exchange given valid ISO codes")
    public void constructsGivenValidCodes() {
        Currency base = new Currency("CHF", "Swiss Franc");
        double baseAmount = 1.0;
        Currency target = new Currency("USD", "United States Dollar");
        double targetAmount = 1.0963;
        LocalDate date = LocalDate.of(2021, Month.DECEMBER, 31);

        CurrencyRegistry registry = new CurrencyRegistry(base, target);

        Exchange expected = new Exchange(base, baseAmount, target, targetAmount, date);
        Exchange actual = Exchange.fromRegistry(registry, "CHF", baseAmount, "USD", targetAmount, date);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Exchange::fromRegistry throws CurrencyNotFoundException given an invalid ISO code")
    public void throwsGivenInvalidCodes() {
        CurrencyRegistry registry = new CurrencyRegistry();

        Assertions.assertThrows(
                CurrencyNotFoundException.class,
                () -> Exchange.fromRegistry(registry, "CHF", 1.0, "CHF", 1.0, LocalDate.EPOCH)
        );
    }
}
