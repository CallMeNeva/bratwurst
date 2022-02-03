// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.CurrencyNotFoundException;
import io.github.callmeneva.bratwurst.model.CurrencyRepository;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.service.response.SpecificDateExchangesDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class SpecificDateExchangesDTOMapper extends AbstractExchangesDTOMapper<SpecificDateExchangesDTO> {

    public SpecificDateExchangesDTOMapper(CurrencyRepository repository) {
        super(repository);
    }

    @Override
    protected Set<Exchange> mapFromRepository(SpecificDateExchangesDTO dataObject, CurrencyRepository repository)
            throws CurrencyNotFoundException {
        // Service implementation detail: null-check on DTO is omitted
        // Super guarantees repository is not null
        Sum commitment = Sum.of(dataObject.base(), dataObject.amount(), repository);
        return dataObject.rates()
                .entrySet()
                .stream()
                .map(codeToValueEntry -> new Exchange(commitment, Sum.of(codeToValueEntry, repository), dataObject.date()))
                .collect(Collectors.toSet());
    }
}
