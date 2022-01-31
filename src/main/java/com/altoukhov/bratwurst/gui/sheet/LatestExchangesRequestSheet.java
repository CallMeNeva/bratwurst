// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import com.altoukhov.bratwurst.service.request.LatestExchangeDataRequest;

public final class LatestExchangesRequestSheet extends AbstractExchangesRequestSheet<LatestExchangeDataRequest> {

    @Override
    public LatestExchangeDataRequest submit() {
        return new LatestExchangeDataRequest(getSelectedBase(), getSelectedTargets(), getSelectedAmount());
    }
}
