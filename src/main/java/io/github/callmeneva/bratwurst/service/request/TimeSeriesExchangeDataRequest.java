// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.util.Arguments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class TimeSeriesExchangeDataRequest extends AbstractExchangeDataRequest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSeriesExchangeDataRequest(Currency base, Collection<Currency> targets, double amount, LocalDate startDate, LocalDate endDate) {
        super(base, targets, amount);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = Arguments.checkNull(startDate, "startDate");
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getEndpointName() {
        String name = DATE_FORMATTER.format(startDate) + "..";
        return (endDate == null) ? name : (name + DATE_FORMATTER.format(endDate));
    }
}
