// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.service.response.TimeSeriesExchangeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;

@DisplayName("TimeSeriesExchangeDTOMapper tests")
class TimeSeriesExchangeDTOMapperTests {

    @Test
    @DisplayName("TimeSeriesExchangeDTOMapper::map maps correctly")
    void maps() {
        Sum commitment = new Sum("EUR", 5.0);

        LocalDate firstDate = LocalDate.of(2000, Month.JANUARY, 3);
        Exchange firstDateEuroToFranc = new Exchange(commitment, new Sum("CHF", 8.0215), firstDate);
        Exchange firstDateEuroToDollar = new Exchange(commitment, new Sum("USD", 5.045), firstDate);

        LocalDate secondDate = LocalDate.of(2000, Month.JANUARY, 4);
        Exchange secondDateEuroToFranc = new Exchange(commitment, new Sum("CHF", 8.0265), secondDate);
        Exchange secondDateEuroToDollar = new Exchange(commitment, new Sum("USD", 5.1525), secondDate);

        TimeSeriesExchangeDTOMapper mapper = TimeSeriesExchangeDTOMapper.INSTANCE;
        TimeSeriesExchangeDTO dataObject = new TimeSeriesExchangeDTO(
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
}
