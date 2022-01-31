// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.response.mapper;

import com.altoukhov.bratwurst.model.CurrencyRegistry;
import com.altoukhov.bratwurst.model.Exchange;

import java.util.Set;

public abstract class AbstractExchangesDTOMapper<D> implements DTOMapper<D, Set<Exchange>> {

    public abstract Set<Exchange> map(D dataObject, CurrencyRegistry registry) throws DTOMappingException;

    @Override
    public final Set<Exchange> map(D dataObject) throws DTOMappingException {
        return map(dataObject, CurrencyRegistry.GLOBAL);
    }
}
