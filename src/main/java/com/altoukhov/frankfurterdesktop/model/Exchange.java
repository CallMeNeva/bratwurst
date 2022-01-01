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

package com.altoukhov.frankfurterdesktop.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Models an immutable currency exchange. Each one is represented by:
 * <ul>
 *     <li>the base currency;</li>
 *     <li>the requested monetary amount of the base currency;</li>
 *     <li>the target currency;</li>
 *     <li>the resulting monetary amount of the target currency;</li>
 *     <li>the publication date of the exchange's rate.</li>
 * </ul>
 *
 * @see Currency
 * @see CurrencyRegistry
 */
public record Exchange(Currency base,
                       double baseAmount,
                       Currency target,
                       double targetAmount,
                       LocalDate date) {

    public Exchange {
        Objects.requireNonNull(base, "Provided base is null");
        Objects.requireNonNull(target, "Provided target is null");
        Objects.requireNonNull(date, "Provided date is null");
    }

    public static Exchange fromRegistry(CurrencyRegistry registry,
                                        String baseCode,
                                        double baseAmount,
                                        String targetCode,
                                        double targetAmount,
                                        LocalDate date) throws CurrencyNotFoundException {
        Objects.requireNonNull(registry, "Provided registry is null");

        Currency base = registry.find(baseCode);
        Currency target = registry.find(targetCode);

        return new Exchange(base, baseAmount, target, targetAmount, date);
    }

    public static Exchange fromRegistry(String baseCode,
                                        double baseAmount,
                                        String targetCode,
                                        double targetAmount,
                                        LocalDate date) throws CurrencyNotFoundException {
        return fromRegistry(CurrencyRegistry.GLOBAL, baseCode, baseAmount, targetCode, targetAmount, date);
    }
}
