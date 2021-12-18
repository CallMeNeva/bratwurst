package com.altoukhov.frankfurterdesktop.service.request;

import com.altoukhov.frankfurterdesktop.model.Currency;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

public final class HistoricalExchangeRatesRequest extends AbstractExchangeRatesRequest {

    private LocalDate date;

    public HistoricalExchangeRatesRequest(Currency base, Collection<Currency> targets, LocalDate date) {
        super(base, targets);
        setDate(date);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    @Override
    protected String getEndpointName() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
