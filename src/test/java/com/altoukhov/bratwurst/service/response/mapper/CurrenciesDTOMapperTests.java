// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.response.mapper;

import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.service.response.CurrenciesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

@DisplayName("CurrenciesDTOMapper Tests")
class CurrenciesDTOMapperTests {

    @Test
    @DisplayName("CurrenciesDTOMapper::map maps correctly")
    void maps() {
        Currency euro = new Currency("EUR", "Euro");
        Currency swissFranc = new Currency("CHF", "Swiss Franc");
        Currency unitedStatesDollar = new Currency("USD", "United States Dollar");

        CurrenciesDTOMapper mapper = CurrenciesDTOMapper.INSTANCE;
        CurrenciesDTO dataObject = new CurrenciesDTO();
        dataObject.put("EUR", "Euro");
        dataObject.put("CHF", "Swiss Franc");
        dataObject.put("USD", "United States Dollar");

        Set<Currency> expected = Set.of(euro, swissFranc, unitedStatesDollar);
        Set<Currency> actual = mapper.map(dataObject);

        Assertions.assertEquals(expected, actual);
    }
}
