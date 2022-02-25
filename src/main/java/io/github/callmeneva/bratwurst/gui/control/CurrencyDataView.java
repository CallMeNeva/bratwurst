// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.model.Currency;

public class CurrencyDataView extends AbstractDataView<Currency> {

    public CurrencyDataView() {
        appendColumn("table.column.currency.code", Currency::code);
        appendColumn("table.column.currency.name", Currency::name);
    }
}
