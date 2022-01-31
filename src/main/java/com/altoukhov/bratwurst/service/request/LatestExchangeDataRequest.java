// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.request;

import com.altoukhov.bratwurst.model.Currency;

import java.util.Collection;

public final class LatestExchangeDataRequest extends AbstractExchangeDataRequest {

    public LatestExchangeDataRequest(Currency base, Collection<Currency> targets, double amount) {
        super(base, targets, amount);
    }

    @Override
    protected String getEndpointName() {
        return "latest";
    }
}
