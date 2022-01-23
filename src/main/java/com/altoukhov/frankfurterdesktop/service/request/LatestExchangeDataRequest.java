// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.service.request;

import com.altoukhov.frankfurterdesktop.model.Currency;

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
