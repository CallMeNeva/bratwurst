/*
 * Copyright 2021, 2022 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.altoukhov.frankfurterdesktop.service.response.mapper;

import com.altoukhov.frankfurterdesktop.model.CurrencyNotFoundException;
import com.altoukhov.frankfurterdesktop.model.CurrencyRegistry;
import com.altoukhov.frankfurterdesktop.model.Exchange;
import com.altoukhov.frankfurterdesktop.service.response.TimeSeriesExchangesDTO;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class TimeSeriesExchangesDTOMapper extends AbstractExchangesDTOMapper<TimeSeriesExchangesDTO> {

    public static final TimeSeriesExchangesDTOMapper INSTANCE = new TimeSeriesExchangesDTOMapper();

    private TimeSeriesExchangesDTOMapper() {}

    @Override
    public Set<Exchange> map(TimeSeriesExchangesDTO dataObject, CurrencyRegistry registry) throws DTOMappingException {
        Objects.requireNonNull(dataObject, "Provided DTO is null");
        Objects.requireNonNull(registry, "Provided registry is null");

        try {
            return dataObject.rates()
                    .entrySet()
                    .stream() /* [Date to [code to value]]  entries */
                    .flatMap(dateToRatesEntry -> {
                        String baseCode = dataObject.base();
                        double baseAmount = dataObject.amount();
                        LocalDate date = dateToRatesEntry.getKey();

                        return dateToRatesEntry.getValue()
                                .entrySet()
                                .stream() /* [Code to value] entries */
                                .map(codeToValueEntry -> {
                                    String targetCode = codeToValueEntry.getKey();
                                    double targetAmount = codeToValueEntry.getValue();
                                    return Exchange.fromRegistry(registry, baseCode, baseAmount, targetCode, targetAmount, date);
                                });
                    })
                    .collect(Collectors.toSet());
        } catch (CurrencyNotFoundException e) {
            throw new DTOMappingException(e);
        }
    }
}
