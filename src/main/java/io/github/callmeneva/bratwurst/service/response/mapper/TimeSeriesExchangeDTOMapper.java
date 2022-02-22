// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.response.mapper;

import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.service.response.TimeSeriesExchangeDTO;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public enum TimeSeriesExchangeDTOMapper implements DTOMapper<TimeSeriesExchangeDTO, Set<Exchange>> {
    INSTANCE;

    @Override
    public Set<Exchange> map(TimeSeriesExchangeDTO dataObject) {
        Sum commitment = new Sum(dataObject.base(), dataObject.amount());
        return dataObject.rates()
                .entrySet()
                .stream()
                .flatMap(dateToRatesEntry -> {
                    LocalDate date = dateToRatesEntry.getKey();
                    return dateToRatesEntry.getValue()
                            .entrySet()
                            .stream()
                            .map(codeToValueEntry -> new Exchange(commitment, Sum.ofMapEntry(codeToValueEntry), date));
                })
                .collect(Collectors.toSet());
    }
}
