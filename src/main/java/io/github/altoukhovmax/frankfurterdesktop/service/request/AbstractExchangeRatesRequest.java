package io.github.altoukhovmax.frankfurterdesktop.service.request;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractExchangeRatesRequest implements DataRequest {

    private Currency baseCurrency;
    private List<Currency> targetCurrencies;

    protected AbstractExchangeRatesRequest(@NotNull Currency baseCurrency, @NotNull List<Currency> targetCurrencies) {
        this.baseCurrency = baseCurrency;
        this.targetCurrencies = targetCurrencies; /* Empty list is valid and represents all targets */
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(@NotNull Currency newBaseCurrency) {
        this.baseCurrency = newBaseCurrency;
    }

    public List<Currency> getTargetCurrencies() {
        return targetCurrencies;
    }

    public void setTargetCurrencies(@NotNull List<Currency> newTargetCurrencies) {
        this.targetCurrencies = newTargetCurrencies;
    }

    protected abstract String getEndpointName();

    @Override
    public String toURIPath() {
        String path = String.format("%s?from=%s", getEndpointName(), baseCurrency.code());
        return targetCurrencies.isEmpty() ? path : (path + "&to=" + targetCurrencies.stream()
                .map(Currency::code)
                .collect(Collectors.joining(",")));
    }
}
