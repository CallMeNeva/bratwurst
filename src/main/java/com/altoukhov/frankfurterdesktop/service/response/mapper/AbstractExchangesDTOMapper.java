/*
 * Copyright 2022 Maxim Altoukhov
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

import com.altoukhov.frankfurterdesktop.model.CurrencyRegistry;
import com.altoukhov.frankfurterdesktop.model.Exchange;

import java.util.Set;

public abstract class AbstractExchangesDTOMapper<D> implements DTOMapper<D, Set<Exchange>> {

    public abstract Set<Exchange> map(D dataObject, CurrencyRegistry registry) throws DTOMappingException;

    @Override
    public final Set<Exchange> map(D dataObject) throws DTOMappingException {
        return map(dataObject, CurrencyRegistry.GLOBAL);
    }
}
