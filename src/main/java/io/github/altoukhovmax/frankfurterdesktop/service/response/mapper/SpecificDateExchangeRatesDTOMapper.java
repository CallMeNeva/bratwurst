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
import io.github.altoukhovmax.frankfurterdesktop.service.response.SpecificDateExchangeRatesDTO;

import java.util.Set;
import java.util.stream.Collectors;

public enum SpecificDateExchangeRatesDTOMapper implements ExchangeRatesDTOMapper<SpecificDateExchangeRatesDTO> {
    INSTANCE;

    @Override
    public Set<ExchangeRate> map(SpecificDateExchangeRatesDTO dataObject) throws DTOMappingException {
        /* Internal usage: no null-checks */
        try {
            return dataObject.rates()
                    .entrySet()
                    .stream()
                    .map(entry -> ExchangeRate.of(dataObject.base(), entry.getKey(), entry.getValue(), dataObject.date()))
                    .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new DTOMappingException(e);
        }
    }
}
