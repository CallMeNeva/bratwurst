// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.model.CurrencyRepository;
import io.github.callmeneva.bratwurst.service.request.TimeSeriesExchangeDataRequest;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class TimeSeriesExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<TimeSeriesExchangeDataRequest> {

    // FIXME: Externalize UI strings
    private static final String START_DATE_PICKER_LABEL = "Start date";
    private static final String END_DATE_PICKER_LABEL = "End date";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.now()
            .minusWeeks(1);

    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;

    public TimeSeriesExchangeDataRequestSheet(CurrencyRepository currencyRepository) {
        super(currencyRepository);
        startDatePicker = appendEditor(START_DATE_PICKER_LABEL, new DatePicker(DEFAULT_START_DATE));
        endDatePicker = appendEditor(END_DATE_PICKER_LABEL, new DatePicker());
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
        selectStartDate(DEFAULT_START_DATE);
        selectEndDate(null);
    }

    private static boolean rangeValid(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            return false;
        }
        boolean minimalCondition = startDate.isBefore(LocalDate.now());
        return (endDate == null) ? minimalCondition : (minimalCondition && startDate.isBefore(endDate));
    }
}
