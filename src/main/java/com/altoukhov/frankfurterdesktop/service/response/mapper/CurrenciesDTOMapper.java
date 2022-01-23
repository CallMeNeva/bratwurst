// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.service.response.mapper;

import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.service.response.CurrenciesDTO;

import java.util.Set;

public enum CurrenciesDTOMapper implements DTOMapper<CurrenciesDTO, Set<Currency>> {
    INSTANCE;

    @Override
    public Set<Currency> map(CurrenciesDTO dataObject) throws DTOMappingException {
        return Currency.ofMap(dataObject);
    }
}
