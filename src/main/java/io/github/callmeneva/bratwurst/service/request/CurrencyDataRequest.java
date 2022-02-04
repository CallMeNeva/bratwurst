// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import org.apache.hc.core5.http.URIScheme;

public class CurrencyDataRequest extends AbstractDataRequest {

    public CurrencyDataRequest(URIScheme scheme, String hostname, int port) {
        super(scheme, hostname, port);
    }

    @Override
    protected String getEndpointName() {
        return "currencies";
    }
}
