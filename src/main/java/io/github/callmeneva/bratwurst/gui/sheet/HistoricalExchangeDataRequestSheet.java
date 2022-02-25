// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.service.request.HistoricalExchangeDataRequest;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.Month;

public class HistoricalExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<HistoricalExchangeDataRequest> {

    private static final LocalDate DEFAULT_DATE = LocalDate.of(2000, Month.JANUARY, 1);

    private final DatePicker datePicker;

    public HistoricalExchangeDataRequestSheet() {
        datePicker = appendEditor("sheet.request.input.date", new DatePicker(DEFAULT_DATE));
        // HACK: This DatePicker must never contain null, but this is a skin-deep solution. Same issue is present in time-series request sheet.
        datePicker.setEditable(false);
    }

    @Override
    public HistoricalExchangeDataRequest submit() {
        return new HistoricalExchangeDataRequest(getBaseCode(), getTargetCodes(), getAmount(), datePicker.getValue());
    }

    @Override
    public void reset() {
        super.reset();
        datePicker.setValue(DEFAULT_DATE);
    }
}
