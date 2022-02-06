// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.converter.SumStringConverter;
import io.github.callmeneva.bratwurst.model.Exchange;
import javafx.util.converter.LocalDateStringConverter;

import java.time.format.FormatStyle;

public class ExchangeDataView extends AbstractDataView<Exchange> {

    public ExchangeDataView() {
        appendColumn("table.column.exchange.commitment", Exchange::commitment, new SumStringConverter());
        appendColumn("table.column.exchange.result", Exchange::result, new SumStringConverter());
        appendColumn("table.column.exchange.date", Exchange::date, new LocalDateStringConverter(FormatStyle.LONG));
    }
}
