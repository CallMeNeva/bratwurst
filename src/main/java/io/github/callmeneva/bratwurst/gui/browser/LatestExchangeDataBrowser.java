// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.browser;

import io.github.callmeneva.bratwurst.gui.control.ExchangeDataView;
import io.github.callmeneva.bratwurst.gui.sheet.LatestExchangeDataRequestSheet;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.service.LatestExchangeDataFetcher;
import io.github.callmeneva.bratwurst.service.request.LatestExchangeDataRequest;

public class LatestExchangeDataBrowser extends DataBrowser<Exchange, LatestExchangeDataRequest> {

    public LatestExchangeDataBrowser() {
        super(new LatestExchangeDataFetcher(), new LatestExchangeDataRequestSheet(), new ExchangeDataView());
    }
}
