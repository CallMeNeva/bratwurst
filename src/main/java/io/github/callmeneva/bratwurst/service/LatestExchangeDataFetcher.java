// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.service.request.LatestExchangeDataRequest;
import io.github.callmeneva.bratwurst.service.response.SpecificDateExchangeDTO;
import io.github.callmeneva.bratwurst.service.response.mapper.SpecificDateExchangeDTOMapper;

import java.util.Set;

public class LatestExchangeDataFetcher extends AbstractDataFetcher<Set<Exchange>, LatestExchangeDataRequest> {

    @Override
    public Set<Exchange> fetch(LatestExchangeDataRequest request) throws DataFetchFailureException {
        return fetch(request, SpecificDateExchangeDTO.class, new SpecificDateExchangeDTOMapper());
    }
}
