// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import com.altoukhov.bratwurst.model.CurrencyRepository;
import com.altoukhov.bratwurst.service.request.LatestExchangeDataRequest;

public class LatestExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<LatestExchangeDataRequest> {

    public LatestExchangeDataRequestSheet(CurrencyRepository currencyRepository) {
        super(currencyRepository);
    }

    @Override
    public LatestExchangeDataRequest submit() {
        return new LatestExchangeDataRequest(getSelectedBase(), getSelectedTargets(), getSelectedAmount());
    }
}
