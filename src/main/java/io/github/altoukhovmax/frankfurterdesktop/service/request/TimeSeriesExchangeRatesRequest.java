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

package io.github.altoukhovmax.frankfurterdesktop.service.request;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

public final class TimeSeriesExchangeRatesRequest extends AbstractExchangeRatesRequest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSeriesExchangeRatesRequest(Currency base, Collection<Currency> targets, LocalDate startDate, LocalDate endDate) {
        super(base, targets);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = Objects.requireNonNull(startDate, "Provided start date is null");
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate; /* Nulls allowed */
    }

    @Override
    protected String getEndpointName() {
        String name = DATE_FORMATTER.format(startDate) + "..";
        return Objects.isNull(endDate) ? name : (name + DATE_FORMATTER.format(endDate));
    }
}
