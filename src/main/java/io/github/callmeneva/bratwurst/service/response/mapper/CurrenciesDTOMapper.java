// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.service.response.CurrenciesDTO;

import java.util.Set;
import java.util.stream.Collectors;

public enum CurrenciesDTOMapper implements DTOMapper<CurrenciesDTO, Set<Currency>> {
    INSTANCE;

    @Override
    public Set<Currency> map(CurrenciesDTO dataObject) throws DTOMappingException {
        // Service implementation detail: null-check on DTO is omitted
        return dataObject.entrySet()
                .stream()
                .map(Currency::of)
                .collect(Collectors.toSet());
    }
}
