package io.github.altoukhovmax.frankfurterdesktop.service.request;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeSeriesExchangeRatesRequest extends AbstractExchangeRatesRequest {

    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSeriesExchangeRatesRequest(@NotNull Currency baseCurrency,
                                          @NotNull List<Currency> targetCurrencies,
                                          @NotNull LocalDate startDate,
                                          @Nullable LocalDate endDate) {
        super(baseCurrency, targetCurrencies);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull LocalDate newStartDate) {
        this.startDate = newStartDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable LocalDate newEndDate) {
        this.endDate = newEndDate;
    }

    @Override
    protected String getEndpointName() {
        String name = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "..";
        return endDate == null ? name : (name + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
