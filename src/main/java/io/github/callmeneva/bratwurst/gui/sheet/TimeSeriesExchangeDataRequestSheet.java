// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.gui.control.DefaultableDatePicker;
import io.github.callmeneva.bratwurst.service.request.TimeSeriesExchangeDataRequest;

import java.time.LocalDate;

public class TimeSeriesExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<TimeSeriesExchangeDataRequest> {

    private final DefaultableDatePicker startDatePicker;
    private final DefaultableDatePicker endDatePicker;

    public TimeSeriesExchangeDataRequestSheet() {
        startDatePicker = appendEditor("sheet.request.input.date.start", new DefaultableDatePicker(() -> {
            LocalDate today = LocalDate.now();
            return today.minusWeeks(1);
        }));
        endDatePicker = appendEditor("sheet.request.input.date.end", new DefaultableDatePicker());
    }

    @Override
    public TimeSeriesExchangeDataRequest submit() {
        return new TimeSeriesExchangeDataRequest(
                getInputtedScheme(),
                getInputtedHostname(),
                getInputtedPort(),
                getInputtedBaseCode(),
                getInputtedTargetCodes(),
                getInputtedAmount(),
                startDatePicker.getValue(),
                endDatePicker.getValue()
        );
    }

    @Override
    public void reset() {
        super.reset();
        startDatePicker.reset();
        endDatePicker.reset();
    }
}
