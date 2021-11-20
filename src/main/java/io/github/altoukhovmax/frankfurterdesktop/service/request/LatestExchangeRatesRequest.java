package io.github.altoukhovmax.frankfurterdesktop.service.request;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;

import java.util.Collection;

public class LatestExchangeRatesRequest extends AbstractExchangeRatesRequest {

    public LatestExchangeRatesRequest(Currency base, Collection<Currency> targets) {
        super(base, targets);
    }

    @Override
    protected String getEndpointName() {
        return "latest";
    }
}
