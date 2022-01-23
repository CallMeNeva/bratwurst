// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.service.request;

import com.altoukhov.frankfurterdesktop.model.Currency;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

public final class TimeSeriesExchangeDataRequest extends AbstractExchangeDataRequest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSeriesExchangeDataRequest(Currency base,
                                         Collection<Currency> targets,
                                         double amount,
                                         LocalDate startDate,
                                         LocalDate endDate) {
        super(base, targets, amount);
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
