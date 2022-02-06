// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.gui.control.DefaultableDatePicker;
import io.github.callmeneva.bratwurst.service.request.HistoricalExchangeDataRequest;

import java.time.LocalDate;
import java.time.Month;

public class HistoricalExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<HistoricalExchangeDataRequest> {

    private static final LocalDate DEFAULT_DATE = LocalDate.of(2000, Month.JANUARY, 1);

    private final DefaultableDatePicker datePicker;

    public HistoricalExchangeDataRequestSheet() {
        datePicker = appendEditor("sheet.request.input.date", new DefaultableDatePicker(DEFAULT_DATE));
    }

    @Override
    public HistoricalExchangeDataRequest submit() {
        return new HistoricalExchangeDataRequest(
                getInputtedScheme(),
                getInputtedHostname(),
                getInputtedPort(),
                getInputtedBaseCode(),
                getInputtedTargetCodes(),
                getInputtedAmount(),
                datePicker.getValue()
        );
    }

    @Override
    public void reset() {
        super.reset();
        datePicker.reset();
    }
}
