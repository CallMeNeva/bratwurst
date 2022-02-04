// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import org.apache.hc.core5.http.URIScheme;

import java.util.List;

public class LatestExchangeDataRequest extends AbstractExchangeDataRequest {

    public LatestExchangeDataRequest(URIScheme scheme,
                                     String hostname,
                                     int port,
                                     String baseCurrencyCode,
                                     List<String> targetCurrencyCodes,
                                     double amount) {
        super(scheme, hostname, port, baseCurrencyCode, targetCurrencyCodes, amount);
    }

    @Override
    protected String getEndpointName() {
        return "latest";
    }
}
