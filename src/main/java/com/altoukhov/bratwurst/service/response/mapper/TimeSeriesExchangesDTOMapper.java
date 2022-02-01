// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.response.mapper;

import com.altoukhov.bratwurst.model.CurrencyNotFoundException;
import com.altoukhov.bratwurst.model.CurrencyRepository;
import com.altoukhov.bratwurst.model.Exchange;
import com.altoukhov.bratwurst.model.Sum;
import com.altoukhov.bratwurst.service.response.TimeSeriesExchangesDTO;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public final class TimeSeriesExchangesDTOMapper extends AbstractExchangesDTOMapper<TimeSeriesExchangesDTO> {

    public TimeSeriesExchangesDTOMapper(CurrencyRepository repository) {
        super(repository);
    }

    @Override
    protected Set<Exchange> mapFromRepository(TimeSeriesExchangesDTO dataObject, CurrencyRepository repository)
            throws CurrencyNotFoundException {
        // Service implementation detail: null-check on DTO is omitted
        // Super guarantees repository is not null
        Sum commitment = Sum.of(dataObject.base(), dataObject.amount(), repository);
        return dataObject.rates()
                .entrySet()
                .stream()
                .flatMap(dateToRatesEntry -> {
                    LocalDate date = dateToRatesEntry.getKey();
                    return dateToRatesEntry.getValue()
                            .entrySet()
                            .stream()
                            .map(codeToValueEntry -> new Exchange(commitment, Sum.of(codeToValueEntry, repository), date));
                })
                .collect(Collectors.toSet());
    }
}
