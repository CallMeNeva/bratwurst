package io.github.altoukhovmax.frankfurterdesktop.service.request;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LatestExchangeRatesRequest extends AbstractExchangeRatesRequest {

    public LatestExchangeRatesRequest(@NotNull Currency baseCurrency, @NotNull List<Currency> targetCurrencies) {
        super(baseCurrency, targetCurrencies);
    }

    @Override
    protected String getEndpointName() {
        return "latest";
    }
}
