// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.service.request.TimeSeriesExchangeDataRequest;
import io.github.callmeneva.bratwurst.service.response.TimeSeriesExchangeDTO;
import io.github.callmeneva.bratwurst.service.response.mapper.TimeSeriesExchangeDTOMapper;

import java.util.Set;

public class TimeSeriesExchangeDataFetcher extends AbstractDataFetcher<Set<Exchange>, TimeSeriesExchangeDataRequest> {

    @Override
    public Set<Exchange> fetch(TimeSeriesExchangeDataRequest request) throws DataFetchFailureException {
        return fetch(request, TimeSeriesExchangeDTO.class, new TimeSeriesExchangeDTOMapper());
    }
}
