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

package com.altoukhov.frankfurterdesktop.service.response.mapper;

import com.altoukhov.frankfurterdesktop.model.CurrencyRegistry;
import com.altoukhov.frankfurterdesktop.service.response.TimeSeriesExchangeRatesDTO;
import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.model.ExchangeRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;

@DisplayName("TimeSeriesExchangeRatesDTOMapper Tests")
public final class TimeSeriesExchangeRatesDTOMapperTests {

    @Test
    @DisplayName("TimeSeriesExchangeRatesDTOMapper::map correctly maps valid DTO")
    void maps() {
        Currency unitedStatesDollar = new Currency("USD", "United States Dollar");
        Currency swissFranc = new Currency("CHF", "Swiss Franc");
        CurrencyRegistry.INSTANCE.update(unitedStatesDollar, swissFranc);

        BigDecimal rateValue = BigDecimal.valueOf(0.91915);
        LocalDate date = LocalDate.of(2021, Month.DECEMBER, 26);

        TimeSeriesExchangeRatesDTO dataObject = new TimeSeriesExchangeRatesDTO(
                unitedStatesDollar.code(),
                Map.of(date, Map.of(swissFranc.code(), rateValue))
        );

        Set<ExchangeRate> expected = Set.of(new ExchangeRate(unitedStatesDollar, swissFranc, rateValue, date));
        Set<ExchangeRate> actual = TimeSeriesExchangeRatesDTOMapper.INSTANCE.map(dataObject);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("TimeSeriesExchangeRatesDTOMapper::map throws DTOMappingException on unidentified currency")
    void throwsOnUnidentifiedCurrency() {
        CurrencyRegistry.INSTANCE.update();

        TimeSeriesExchangeRatesDTO dataObject = new TimeSeriesExchangeRatesDTO(
                "USD",
                Map.of(LocalDate.now(), Map.of("USD", BigDecimal.ONE))
        );

        Assertions.assertThrows(DTOMappingException.class, () -> TimeSeriesExchangeRatesDTOMapper.INSTANCE.map(dataObject));
    }
}
