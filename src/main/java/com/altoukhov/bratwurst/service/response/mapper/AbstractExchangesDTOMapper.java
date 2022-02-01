// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.response.mapper;

import com.altoukhov.bratwurst.model.CurrencyNotFoundException;
import com.altoukhov.bratwurst.model.CurrencyRepository;
import com.altoukhov.bratwurst.model.Exchange;
import com.altoukhov.bratwurst.util.Arguments;

import java.util.Set;

public abstract class AbstractExchangesDTOMapper<D> implements DTOMapper<D, Set<Exchange>> {

    private final CurrencyRepository repository;

    protected AbstractExchangesDTOMapper(CurrencyRepository repository) {
        this.repository = Arguments.checkNull(repository, "repository");
    }

    protected abstract Set<Exchange> mapFromRepository(D dataObject, CurrencyRepository repository) throws CurrencyNotFoundException;

    @Override
    public final Set<Exchange> map(D dataObject) throws DTOMappingException {
        try {
            return mapFromRepository(dataObject, repository);
        } catch (CurrencyNotFoundException e) {
            throw new DTOMappingException(e);
        }
    }
}
