// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.util.Arguments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class HistoricalExchangeDataRequest extends AbstractExchangeDataRequest {

    private LocalDate date;

    public HistoricalExchangeDataRequest(Currency base, Collection<Currency> targets, double amount, LocalDate date) {
        super(base, targets, amount);
        setDate(date);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = Arguments.checkNull(date, "date");
    }

    @Override
    public String getEndpointName() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
