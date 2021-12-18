package com.altoukhov.frankfurterdesktop.service.request;

import com.altoukhov.frankfurterdesktop.model.Currency;

import java.util.Collection;

public final class LatestExchangeRatesRequest extends AbstractExchangeRatesRequest {

    public LatestExchangeRatesRequest(Currency base, Collection<Currency> targets) {
        super(base, targets);
    }

    @Override
    protected String getEndpointName() {
        return "latest";
    }
}
