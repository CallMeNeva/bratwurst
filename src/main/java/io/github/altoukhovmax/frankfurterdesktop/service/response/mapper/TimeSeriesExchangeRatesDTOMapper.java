/*
 * Copyright 2021 Maxim Altoukhov
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

package io.github.altoukhovmax.frankfurterdesktop.service.response.mapper;

import io.github.altoukhovmax.frankfurterdesktop.model.ExchangeRate;
import io.github.altoukhovmax.frankfurterdesktop.service.response.TimeSeriesExchangeRatesDTO;

import java.util.Set;
import java.util.stream.Collectors;

public enum TimeSeriesExchangeRatesDTOMapper implements DTOMapper<TimeSeriesExchangeRatesDTO, Set<ExchangeRate>> {
    INSTANCE;

    @Override
    public Set<ExchangeRate> map(TimeSeriesExchangeRatesDTO dataObj) throws DTOMappingException {
        try {
            return dataObj.rates()
                    .entrySet()
                    .stream() /* Date-to-rates mappings/entries */
                    .flatMap(e1 -> e1.getValue()
                            .entrySet()
                            .stream() /* Currency-code-to-rate-value mappings/entries */
                            .map(e2 -> new ExchangeRate(dataObj.base(), e2.getKey(), e2.getValue(), e1.getKey())))
                    .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new DTOMappingException(e);
        }
    }
}
