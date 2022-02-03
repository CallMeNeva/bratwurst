// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import io.github.callmeneva.bratwurst.model.Currency;

import java.util.Collection;

public class LatestExchangeDataRequest extends AbstractExchangeDataRequest {

    public LatestExchangeDataRequest(Currency base, Collection<Currency> targets, double amount) {
        super(base, targets, amount);
    }

    @Override
    public String getEndpointName() {
        return "latest";
    }
}
