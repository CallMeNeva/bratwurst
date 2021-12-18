package com.altoukhov.frankfurterdesktop.service.request;

import com.altoukhov.frankfurterdesktop.model.Currency;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

public final class TimeSeriesExchangeRatesRequest extends AbstractExchangeRatesRequest {

    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSeriesExchangeRatesRequest(Currency base,
                                          Collection<Currency> targets,
                                          LocalDate startDate,
                                          LocalDate endDate) {
        super(base, targets);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = Objects.requireNonNull(startDate);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate; /* Nulls allowed */
    }

    @Override
    protected String getEndpointName() {
        String name = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "..";
        return endDate == null ? name : (name + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
