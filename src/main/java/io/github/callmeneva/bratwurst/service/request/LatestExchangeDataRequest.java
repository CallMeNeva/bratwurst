// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import java.util.List;

public class LatestExchangeDataRequest extends AbstractExchangeDataRequest {

    public LatestExchangeDataRequest(String baseCurrencyCode, List<String> targetCurrencyCodes, double amount) {
        super(baseCurrencyCode, targetCurrencyCodes, amount);
    }

    @Override
    public String getEndpointName() {
        return "latest";
    }
}
