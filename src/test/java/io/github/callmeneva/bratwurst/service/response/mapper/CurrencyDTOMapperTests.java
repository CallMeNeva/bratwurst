// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.service.response.CurrencyDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

@DisplayName("CurrencyDTOMapper tests")
class CurrencyDTOMapperTests {

    @Test
    @DisplayName("CurrencyDTOMapper::map maps correctly")
    void maps() {
        Currency euro = new Currency("EUR", "Euro");
        Currency swissFranc = new Currency("CHF", "Swiss Franc");
        Currency unitedStatesDollar = new Currency("USD", "United States Dollar");

        CurrencyDTOMapper mapper = new CurrencyDTOMapper();
        CurrencyDTO dataObject = new CurrencyDTO();
        dataObject.put("EUR", "Euro");
        dataObject.put("CHF", "Swiss Franc");
        dataObject.put("USD", "United States Dollar");

        Set<Currency> expected = Set.of(euro, swissFranc, unitedStatesDollar);
        Set<Currency> actual = mapper.map(dataObject);

        Assertions.assertEquals(expected, actual);
    }
}
