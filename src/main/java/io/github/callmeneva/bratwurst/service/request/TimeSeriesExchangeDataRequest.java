// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class TimeSeriesExchangeDataRequest extends AbstractExchangeDataRequest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSeriesExchangeDataRequest(String baseCurrencyCode,
                                         List<String> targetCurrencyCodes,
                                         double amount,
                                         LocalDate startDate,
                                         LocalDate endDate) {
        super(baseCurrencyCode, targetCurrencyCodes, amount);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = Objects.requireNonNull(startDate, "Request start date must not be null");
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate; // Null allowed, represents "present day"
    }

    @Override
    public String getEndpointName() {
        String name = DATE_FORMATTER.format(startDate) + "..";
        return (endDate == null) ? name : (name + DATE_FORMATTER.format(endDate));
    }
}
