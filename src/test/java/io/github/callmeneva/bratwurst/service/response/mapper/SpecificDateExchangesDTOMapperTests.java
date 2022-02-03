// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.model.CurrencyRepository;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.service.response.SpecificDateExchangesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;

@DisplayName("SpecificDateExchangesDTOMapper tests")
class SpecificDateExchangesDTOMapperTests {

    @Test
    @DisplayName("SpecificDateExchangesDTOMapper::map maps correctly")
    void maps() {
        Currency euro = new Currency("EUR", "Euro");
        Currency swissFranc = new Currency("CHF", "Swiss Franc");
        Currency unitedStatesDollar = new Currency("USD", "United States Dollar");

        Sum commitment = new Sum(euro, 5.0);
        LocalDate exchangeDate = LocalDate.of(2022, Month.JANUARY, 28);
        Exchange euroToFranc = new Exchange(commitment, new Sum(swissFranc, 5.189), exchangeDate);
        Exchange euroToDollar = new Exchange(commitment, new Sum(unitedStatesDollar, 5.569), exchangeDate);

        CurrencyRepository repository = new CurrencyRepository();
        repository.update(Set.of(euro, swissFranc, unitedStatesDollar));

        SpecificDateExchangesDTOMapper mapper = new SpecificDateExchangesDTOMapper(repository);
        SpecificDateExchangesDTO dataObject = new SpecificDateExchangesDTO(
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

    @Test
    @DisplayName("SpecificDateExchangesDTOMapper::map throws DTOMappingException if failed to find a referenced currency")
    void throwsOnCurrencyNotFound() {
        CurrencyRepository repository = new CurrencyRepository();

        SpecificDateExchangesDTOMapper mapper = new SpecificDateExchangesDTOMapper(repository);
        SpecificDateExchangesDTO dataObject = new SpecificDateExchangesDTO(
                1.0,
                "EUR",
                LocalDate.EPOCH,
                Map.of("EUR", 1.0)
        );

        Assertions.assertThrows(DTOMappingException.class, () -> mapper.map(dataObject));
    }
}
