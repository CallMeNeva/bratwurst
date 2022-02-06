// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.browser;

import io.github.callmeneva.bratwurst.gui.control.ExchangeDataView;
import io.github.callmeneva.bratwurst.gui.sheet.HistoricalExchangeDataRequestSheet;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.service.HistoricalExchangeDataFetcher;
import io.github.callmeneva.bratwurst.service.request.HistoricalExchangeDataRequest;

public class HistoricalExchangeDataBrowser extends DataBrowser<Exchange, HistoricalExchangeDataRequest> {

    public HistoricalExchangeDataBrowser() {
        super(new HistoricalExchangeDataFetcher(), new HistoricalExchangeDataRequestSheet(), new ExchangeDataView());
    }
}
