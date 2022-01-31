// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.request;

public final class CurrencyDataRequest extends AbstractDataRequest {

    public static final CurrencyDataRequest INSTANCE = new CurrencyDataRequest();

    private CurrencyDataRequest() {}

    @Override
    protected String getEndpointName() {
        return "currencies";
    }
}
