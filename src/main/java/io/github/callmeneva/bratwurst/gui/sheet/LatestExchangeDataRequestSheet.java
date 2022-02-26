// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.service.request.LatestExchangeDataRequest;

public class LatestExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<LatestExchangeDataRequest> {

    @Override
    public LatestExchangeDataRequest submit() {
        return new LatestExchangeDataRequest(getBaseCode(), getTargetCodes(), getAmount());
    }
}
