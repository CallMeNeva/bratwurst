// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.service.response.mapper;

import com.altoukhov.frankfurterdesktop.model.CurrencyNotFoundException;
import com.altoukhov.frankfurterdesktop.model.CurrencyRegistry;
import com.altoukhov.frankfurterdesktop.model.Exchange;
import com.altoukhov.frankfurterdesktop.service.response.SpecificDateExchangesDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class SpecificDateExchangesDTOMapper extends AbstractExchangesDTOMapper<SpecificDateExchangesDTO> {

    public static final SpecificDateExchangesDTOMapper INSTANCE = new SpecificDateExchangesDTOMapper();

    private SpecificDateExchangesDTOMapper() {}

    @Override
    public Set<Exchange> map(SpecificDateExchangesDTO dataObject, CurrencyRegistry registry) throws DTOMappingException {
        Objects.requireNonNull(dataObject, "Provided DTO is null");
        Objects.requireNonNull(registry, "Provided registry is null");

        try {
            return dataObject.rates()
                    .entrySet()
                    .stream()
                    .map(entry -> Exchange.fromRegistry(
                            registry,
                            dataObject.base(),
                            dataObject.amount(),
                            entry.getKey(),
                            entry.getValue(),
                            dataObject.date()
                    ))
                    .collect(Collectors.toSet());
        } catch (CurrencyNotFoundException e) {
            throw new DTOMappingException(e);
        }
    }
}
