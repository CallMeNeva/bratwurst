// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.service.response.SpecificDateExchangeDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class SpecificDateExchangeDTOMapper implements DTOMapper<SpecificDateExchangeDTO, Set<Exchange>> {

    @Override
    public Set<Exchange> map(SpecificDateExchangeDTO dataObject) {
        Sum commitment = new Sum(dataObject.base(), dataObject.amount());
        return dataObject.rates()
                .entrySet()
                .stream()
                .map(codeToValueEntry -> new Exchange(commitment, Sum.ofMapEntry(codeToValueEntry), dataObject.date()))
                .collect(Collectors.toSet());
    }
}
