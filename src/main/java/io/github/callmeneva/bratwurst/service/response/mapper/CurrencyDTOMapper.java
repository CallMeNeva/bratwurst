// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.service.response.CurrencyDTO;

import java.util.Set;
import java.util.stream.Collectors;

public enum CurrencyDTOMapper implements DTOMapper<CurrencyDTO, Set<Currency>> {
    INSTANCE;

    @Override
    public Set<Currency> map(CurrencyDTO dataObject) {
        return dataObject.entrySet()
                .stream()
                .map(Currency::ofMapEntry)
                .collect(Collectors.toSet());
    }
}
