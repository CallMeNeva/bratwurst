// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.service.request;

public enum CurrencyDataRequest implements DataRequest {
    INSTANCE;

    @Override
    public String getEndpointName() {
        return "currencies";
    }
}
