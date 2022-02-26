// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class HistoricalExchangeDataRequest extends AbstractExchangeDataRequest {

    private LocalDate date;

    public HistoricalExchangeDataRequest(String baseCurrencyCode, List<String> targetCurrencyCodes, double amount, LocalDate date) {
        super(baseCurrencyCode, targetCurrencyCodes, amount);
        setDate(date);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = Objects.requireNonNull(date, "Request date must not be null");
    }

    @Override
    public String getEndpointName() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
