package io.github.altoukhovmax.frankfurterdesktop.service.request;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoricalExchangeRatesRequest extends AbstractExchangeRatesRequest {

    private LocalDate date;

    public HistoricalExchangeRatesRequest(@NotNull Currency baseCurrency,
                                          @NotNull List<Currency> targetCurrencies,
                                          @NotNull LocalDate date) {
        super(baseCurrency, targetCurrencies);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull LocalDate newDate) {
        this.date = newDate;
    }

    @Override
    protected String getEndpointName() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
