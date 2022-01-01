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

import java.math.BigDecimal;
import java.time.LocalDate;

@DisplayName("ExchangeRate Tests")
public final class ExchangeRateTests {

    private static final Currency EURO = new Currency("EUR", "Euro");
    private static final Currency SWISS_FRANC = new Currency("CHF", "Swiss Franc");
    private static final BigDecimal DUMMY_VALUE = BigDecimal.TEN;
    private static final LocalDate DUMMY_DATE = LocalDate.EPOCH;

    @Test
    @DisplayName("ExchangeRate::of correctly constructs with valid parameters")
    public void ofConstructsOnValidParams() {
        CurrencyRegistry.GLOBAL.update(EURO, SWISS_FRANC);

        Assertions.assertDoesNotThrow(() -> ExchangeRate.of("EUR", "CHF", DUMMY_VALUE, DUMMY_DATE));
    }

    @Test
    @DisplayName("ExchangeRate::of throws IAE if failed to find currencies")
    public void ofThrowsOnCurrencyNotFound() {
        CurrencyRegistry.GLOBAL.update(EURO);

        Assertions.assertThrows(IllegalArgumentException.class, () -> ExchangeRate.of("EUR", "CHF", DUMMY_VALUE, DUMMY_DATE));
    }
}
