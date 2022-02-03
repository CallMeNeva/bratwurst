// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.CurrencyNotFoundException;
import io.github.callmeneva.bratwurst.model.CurrencyRepository;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.util.Arguments;

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
