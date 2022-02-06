// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.browser;

import io.github.callmeneva.bratwurst.gui.control.CurrencyDataView;
import io.github.callmeneva.bratwurst.gui.sheet.CurrencyDataRequestSheet;
import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.service.CurrencyDataFetcher;
import io.github.callmeneva.bratwurst.service.request.CurrencyDataRequest;

public class CurrencyDataBrowser extends DataBrowser<Currency, CurrencyDataRequest> {

    public CurrencyDataBrowser() {
        super(new CurrencyDataFetcher(), new CurrencyDataRequestSheet(), new CurrencyDataView());
    }
}
