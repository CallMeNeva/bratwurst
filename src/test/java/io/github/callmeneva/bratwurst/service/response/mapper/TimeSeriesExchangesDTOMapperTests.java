// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.model.CurrencyRepository;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.service.response.TimeSeriesExchangesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;

@DisplayName("TimeSeriesExchangesDTOMapper tests")
class TimeSeriesExchangesDTOMapperTests {

    @Test
    @DisplayName("TimeSeriesExchangesDTOMapper::map maps correctly")
    void maps() {
        Currency euro = new Currency("EUR", "Euro");
        Currency swissFranc = new Currency("CHF", "Swiss Franc");
        Currency unitedStatesDollar = new Currency("USD", "United States Dollar");

        Sum commitment = new Sum(euro, 5.0);

        LocalDate firstDate = LocalDate.of(2000, Month.JANUARY, 3);
        Exchange firstDateEuroToFranc = new Exchange(commitment, new Sum(swissFranc, 8.0215), firstDate);
        Exchange firstDateEuroToDollar = new Exchange(commitment, new Sum(unitedStatesDollar, 5.045), firstDate);

        LocalDate secondDate = LocalDate.of(2000, Month.JANUARY, 4);
        Exchange secondDateEuroToFranc = new Exchange(commitment, new Sum(swissFranc, 8.0265), secondDate);
        Exchange secondDateEuroToDollar = new Exchange(commitment, new Sum(unitedStatesDollar, 5.1525), secondDate);

        CurrencyRepository repository = new CurrencyRepository();
        repository.update(Set.of(euro, swissFranc, unitedStatesDollar));

        TimeSeriesExchangesDTOMapper mapper = new TimeSeriesExchangesDTOMapper(repository);
        TimeSeriesExchangesDTO dataObject = new TimeSeriesExchangesDTO(
                5.0,
                "EUR",
                Map.of(
                        LocalDate.of(2000, Month.JANUARY, 3), Map.of(
                                "CHF", 8.0215,
                                "USD", 5.045
                        ),
                        LocalDate.of(2000, Month.JANUARY, 4), Map.of(
                                "CHF", 8.0265,
                                "USD", 5.1525
                        )
                )
        );

        Set<Exchange> expected = Set.of(firstDateEuroToFranc, firstDateEuroToDollar, secondDateEuroToFranc, secondDateEuroToDollar);
        Set<Exchange> actual = mapper.map(dataObject);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("TimeSeriesExchangesDTOMapper::map throws DTOMappingException if failed to find a referenced currency")
    void throwsOnCurrencyNotFound() {
        CurrencyRepository repository = new CurrencyRepository();

        TimeSeriesExchangesDTOMapper mapper = new TimeSeriesExchangesDTOMapper(repository);
        TimeSeriesExchangesDTO dataObject = new TimeSeriesExchangesDTO(
                1.0,
                "EUR",
                Map.of(LocalDate.EPOCH, Map.of("EUR", 1.0))
        );

        Assertions.assertThrows(DTOMappingException.class, () -> mapper.map(dataObject));
    }
}
