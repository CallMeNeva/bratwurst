// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.gui.sheet;

import com.altoukhov.frankfurterdesktop.service.request.HistoricalExchangeDataRequest;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.Objects;

public final class HistoricalExchangesRequestSheet extends AbstractExchangesRequestSheet<HistoricalExchangeDataRequest> {

    /*
     * TODO: Externalize UI strings
     */

    private static final String DATE_PICKER_LABEL = "Date";

    private final DatePicker datePicker;

    public HistoricalExchangesRequestSheet() {
        datePicker = appendEditor(DATE_PICKER_LABEL, DatePicker::new);
    }

    public LocalDate getSelectedDate() {
        return datePicker.getValue();
    }

    public void selectDate(LocalDate date) {
        datePicker.setValue(date);
    }

    @Override
    public HistoricalExchangeDataRequest submit() throws InvalidSheetInputException {
        LocalDate selectedDate = getSelectedDate();

        if (Objects.isNull(selectedDate)) {
            throw new InvalidSheetInputException();
        }

        return new HistoricalExchangeDataRequest(getSelectedBase(), getSelectedTargets(), getSelectedAmount(), selectedDate);
    }

    @Override
    public void load(HistoricalExchangeDataRequest entity) {
        super.load(entity);
        selectDate(entity.getDate());
    }

    @Override
    public void clear() {
        super.clear();
        selectDate(null);
    }
}
