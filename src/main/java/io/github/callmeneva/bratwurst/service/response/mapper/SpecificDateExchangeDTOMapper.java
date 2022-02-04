// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.service.response.SpecificDateExchangeDTO;

import java.util.Set;
import java.util.stream.Collectors;

public enum SpecificDateExchangeDTOMapper implements DTOMapper<SpecificDateExchangeDTO, Set<Exchange>> {
    INSTANCE;

    @Override
    public Set<Exchange> map(SpecificDateExchangeDTO dataObject) {
        Sum commitment = new Sum(dataObject.base(), dataObject.amount());
        return dataObject.rates()
                .entrySet()
                .stream()
                .map(codeToValueEntry -> new Exchange(commitment, Sum.ofEntry(codeToValueEntry), dataObject.date()))
                .collect(Collectors.toSet());
    }
}