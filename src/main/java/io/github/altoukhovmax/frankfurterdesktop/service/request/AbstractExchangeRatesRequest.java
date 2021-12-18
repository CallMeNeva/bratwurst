package io.github.altoukhovmax.frankfurterdesktop.service.request;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract sealed class AbstractExchangeRatesRequest implements DataRequest
        permits LatestExchangeRatesRequest, HistoricalExchangeRatesRequest, TimeSeriesExchangeRatesRequest {

    private Currency base;
    private Collection<Currency> targets;

    protected AbstractExchangeRatesRequest(Currency base, Collection<Currency> targets) {
        setBase(base);
        setTargets(targets); /* Empty collection is valid and represents all targets */
    }

    public Currency getBase() {
        return base;
    }

    public void setBase(Currency base) {
        this.base = Objects.requireNonNull(base);
    }

    public Collection<Currency> getTargets() {
        return targets;
    }

    public void setTargets(Collection<Currency> targets) {
        this.targets = Objects.requireNonNull(targets);
    }

    protected abstract String getEndpointName();

    @Override
    public String toURIPath() {
        String path = String.format("%s?from=%s", getEndpointName(), base.code());
        return targets.isEmpty() ? path : (path + "&to=" + targets.stream()
                .map(Currency::code)
                .collect(Collectors.joining(",")));
    }
}
