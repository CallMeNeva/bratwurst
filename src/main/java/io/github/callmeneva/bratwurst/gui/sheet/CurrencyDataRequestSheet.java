// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.service.request.CurrencyDataRequest;

public class CurrencyDataRequestSheet extends AbstractDataRequestSheet<CurrencyDataRequest> {

    @Override
    public CurrencyDataRequest submit() {
        return new CurrencyDataRequest(
                getInputtedScheme(),
                getInputtedHostname(),
                getInputtedPort()
        );
    }
}
