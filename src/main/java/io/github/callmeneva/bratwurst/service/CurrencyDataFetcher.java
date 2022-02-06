// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.service.request.CurrencyDataRequest;
import io.github.callmeneva.bratwurst.service.response.CurrencyDTO;
import io.github.callmeneva.bratwurst.service.response.mapper.CurrencyDTOMapper;

import java.util.Set;

public class CurrencyDataFetcher extends AbstractDataFetcher<Set<Currency>, CurrencyDataRequest> {

    @Override
    public Set<Currency> fetch(CurrencyDataRequest request) throws DataFetchFailureException {
        return fetch(request, CurrencyDTO.class, CurrencyDTOMapper.INSTANCE);
    }
}
