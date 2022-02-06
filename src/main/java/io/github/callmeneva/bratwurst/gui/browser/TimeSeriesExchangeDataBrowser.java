// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.browser;

import io.github.callmeneva.bratwurst.gui.control.ExchangeDataView;
import io.github.callmeneva.bratwurst.gui.sheet.TimeSeriesExchangeDataRequestSheet;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.service.TimeSeriesExchangeDataFetcher;
import io.github.callmeneva.bratwurst.service.request.TimeSeriesExchangeDataRequest;

public class TimeSeriesExchangeDataBrowser extends DataBrowser<Exchange, TimeSeriesExchangeDataRequest> {

    public TimeSeriesExchangeDataBrowser() {
        super(new TimeSeriesExchangeDataFetcher(), new TimeSeriesExchangeDataRequestSheet(), new ExchangeDataView());
    }
}
