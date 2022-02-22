// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.service.response.SpecificDateExchangeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;

@DisplayName("SpecificDateExchangeDTOMapper tests")
class SpecificDateExchangeDTOMapperTests {

    @Test
    @DisplayName("SpecificDateExchangeDTOMapper::map maps correctly")
    void maps() {
        Sum commitment = new Sum("EUR", 5.0);
        LocalDate exchangeDate = LocalDate.of(2022, Month.JANUARY, 28);
        Exchange euroToFranc = new Exchange(commitment, new Sum("CHF", 5.189), exchangeDate);
        Exchange euroToDollar = new Exchange(commitment, new Sum("USD", 5.569), exchangeDate);

        SpecificDateExchangeDTOMapper mapper = new SpecificDateExchangeDTOMapper();
        SpecificDateExchangeDTO dataObject = new SpecificDateExchangeDTO(
                5.0,
                "EUR",
                LocalDate.of(2022, Month.JANUARY, 28),
                Map.of(
                        "CHF", 5.189,
                        "USD", 5.569
                )
        );

        Set<Exchange> expected = Set.of(euroToFranc, euroToDollar);
        Set<Exchange> actual = mapper.map(dataObject);

        Assertions.assertEquals(expected, actual);
    }
}
