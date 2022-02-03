// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

public class CurrencyDataRequest implements DataRequest {

    @Override
    public String getEndpointName() {
        return "currencies";
    }
}
