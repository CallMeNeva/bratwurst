// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import com.altoukhov.bratwurst.model.CurrencyRepository;
import com.altoukhov.bratwurst.service.request.HistoricalExchangeDataRequest;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.Month;

public class HistoricalExchangeDataRequestSheet extends AbstractExchangeDataRequestSheet<HistoricalExchangeDataRequest> {

    // FIXME: Externalize UI strings
    private static final String DATE_PICKER_LABEL = "Date";

    private static final LocalDate DEFAULT_DATE = LocalDate.of(2000, Month.JANUARY, 1);

    private final DatePicker datePicker;

    public HistoricalExchangeDataRequestSheet(CurrencyRepository currencyRepository) {
        super(currencyRepository);
        datePicker = appendEditor(DATE_PICKER_LABEL, new DatePicker(DEFAULT_DATE));
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

        if (selectedDate == null) {
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
        selectDate(DEFAULT_DATE);
    }
}
