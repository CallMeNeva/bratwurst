// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.model.CurrencyRepository;
import io.github.callmeneva.bratwurst.service.request.LatestExchangeDataRequest;

public class LatestExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<LatestExchangeDataRequest> {

    public LatestExchangeDataRequestSheet(CurrencyRepository currencyRepository) {
        super(currencyRepository);
    }

    @Override
    public LatestExchangeDataRequest submit() {
        return new LatestExchangeDataRequest(getSelectedBase(), getSelectedTargets(), getSelectedAmount());
    }
}
