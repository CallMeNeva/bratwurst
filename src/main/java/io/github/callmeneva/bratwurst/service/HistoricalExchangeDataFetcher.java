// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.service.request.HistoricalExchangeDataRequest;
import io.github.callmeneva.bratwurst.service.response.SpecificDateExchangeDTO;
import io.github.callmeneva.bratwurst.service.response.mapper.SpecificDateExchangeDTOMapper;

import java.util.Set;

public class HistoricalExchangeDataFetcher extends AbstractDataFetcher<Set<Exchange>, HistoricalExchangeDataRequest> {

    @Override
    public Set<Exchange> fetch(HistoricalExchangeDataRequest request) throws DataFetchFailureException {
        return fetch(request, SpecificDateExchangeDTO.class, new SpecificDateExchangeDTOMapper());
    }
}
