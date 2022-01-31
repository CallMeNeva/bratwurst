// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import com.altoukhov.bratwurst.service.request.TimeSeriesExchangeDataRequest;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.Objects;

public final class TimeSeriesExchangesRequestSheet extends AbstractExchangesRequestSheet<TimeSeriesExchangeDataRequest> {

    /*
     * TODO: Externalize UI strings
     */

    private static final String START_DATE_PICKER_LABEL = "Start date";
    private static final String END_DATE_PICKER_LABEL = "End date";

    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;

    public TimeSeriesExchangesRequestSheet() {
        startDatePicker = appendEditor(START_DATE_PICKER_LABEL, DatePicker::new);
        endDatePicker = appendEditor(END_DATE_PICKER_LABEL, DatePicker::new);
    }

    public LocalDate getSelectedStartDate() {
        return startDatePicker.getValue();
    }

    public void selectStartDate(LocalDate startDate) {
        startDatePicker.setValue(startDate);
    }

    public LocalDate getSelectedEndDate() {
        return endDatePicker.getValue();
    }

    public void selectEndDate(LocalDate endDate) {
        endDatePicker.setValue(endDate);
    }

    @Override
    public TimeSeriesExchangeDataRequest submit() throws InvalidSheetInputException {
        LocalDate startDate = getSelectedStartDate();
        LocalDate endDate = getSelectedEndDate();

        if (!rangeValid(startDate, endDate)) {
            throw new InvalidSheetInputException();
        }

        return new TimeSeriesExchangeDataRequest(getSelectedBase(), getSelectedTargets(), getSelectedAmount(), startDate, endDate);
    }

    @Override
    public void load(TimeSeriesExchangeDataRequest entity) {
        super.load(entity);
        selectStartDate(entity.getStartDate());
        selectEndDate(entity.getEndDate());
    }

    @Override
    public void clear() {
        super.clear();
        selectStartDate(null);
        selectEndDate(null);
    }

    private static boolean rangeValid(LocalDate startDate, LocalDate endDate) {
        if (Objects.isNull(startDate)) {
            return false;
        }
        boolean minimalCondition = startDate.isBefore(LocalDate.now());
        return Objects.isNull(endDate) ? minimalCondition : (minimalCondition && startDate.isBefore(endDate));
    }
}
