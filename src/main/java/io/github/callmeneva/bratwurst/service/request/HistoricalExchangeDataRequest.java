// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import org.apache.commons.lang3.Validate;
import org.apache.hc.core5.http.URIScheme;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoricalExchangeDataRequest extends AbstractExchangeDataRequest {

    private LocalDate date;

    public HistoricalExchangeDataRequest(URIScheme scheme,
                                         String hostname,
                                         int port,
                                         String baseCurrencyCode,
                                         List<String> targetCurrencyCodes,
                                         double amount,
                                         LocalDate date) {
        super(scheme, hostname, port, baseCurrencyCode, targetCurrencyCodes, amount);
        setDate(date);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = Validate.notNull(date);
    }

    @Override
    protected String getEndpointName() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
