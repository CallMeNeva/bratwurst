// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import org.apache.commons.lang3.Validate;
import org.apache.hc.core5.http.URIScheme;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeSeriesExchangeDataRequest extends AbstractExchangeDataRequest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSeriesExchangeDataRequest(URIScheme scheme,
                                         String hostname,
                                         int port,
                                         String baseCurrencyCode,
                                         List<String> targetCurrencyCodes,
                                         double amount,
                                         LocalDate startDate,
                                         LocalDate endDate) {
        super(scheme, hostname, port, baseCurrencyCode, targetCurrencyCodes, amount);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = Validate.notNull(startDate);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    protected String getEndpointName() {
        String name = DATE_FORMATTER.format(startDate) + "..";
        return (endDate == null) ? name : (name + DATE_FORMATTER.format(endDate));
    }
}
