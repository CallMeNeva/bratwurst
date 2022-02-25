// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.service.request.TimeSeriesExchangeDataRequest;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class TimeSeriesExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<TimeSeriesExchangeDataRequest> {

    private static final LocalDate DEFAULT_START_DATE = createDefaultStartDate();

    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;

    public TimeSeriesExchangeDataRequestSheet() {
        startDatePicker = appendEditor("sheet.request.input.date.start", new DatePicker(DEFAULT_START_DATE));
        // HACK: This DatePicker must never contain null, but this is a skin-deep solution. Same issue is present in historical request sheet.
        startDatePicker.setEditable(false);
        endDatePicker = appendEditor("sheet.request.input.date.end", new DatePicker());
    }

    @Override
    public TimeSeriesExchangeDataRequest submit() {
        return new TimeSeriesExchangeDataRequest(getBaseCode(), getTargetCodes(), getAmount(), startDatePicker.getValue(), endDatePicker.getValue());
    }

    @Override
    public void reset() {
        super.reset();
        startDatePicker.setValue(DEFAULT_START_DATE);
        endDatePicker.setValue(null);
    }

    private static LocalDate createDefaultStartDate() {
        LocalDate today = LocalDate.now();
        return today.minusWeeks(1);
    }
}
