// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.response.mapper;

import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.service.response.CurrenciesDTO;

import java.util.Set;

public enum CurrenciesDTOMapper implements DTOMapper<CurrenciesDTO, Set<Currency>> {
    INSTANCE;

    @Override
    public Set<Currency> map(CurrenciesDTO dataObject) throws DTOMappingException {
        return Currency.ofMap(dataObject);
    }
}
