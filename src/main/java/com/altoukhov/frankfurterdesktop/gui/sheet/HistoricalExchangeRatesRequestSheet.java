/*
 * Copyright 2021 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.altoukhov.frankfurterdesktop.gui.sheet;

import com.altoukhov.frankfurterdesktop.service.request.HistoricalExchangeRatesRequest;
import javafx.scene.control.DatePicker;
import org.controlsfx.validation.Validator;

import java.time.LocalDate;
import java.util.Objects;

public final class HistoricalExchangeRatesRequestSheet extends AbstractExchangeRatesRequestSheet<HistoricalExchangeRatesRequest> {

    private static final String DATE_PICKER_LABEL = "Date";
    private static final String NO_DATE_SELECTION_MESSAGE = "A date must be selected";

    private final DatePicker datePicker;

    public HistoricalExchangeRatesRequestSheet() {
        datePicker = appendDatePicker(DATE_PICKER_LABEL, Validator.createEmptyValidator(NO_DATE_SELECTION_MESSAGE));
    }

    public LocalDate getSelectedDate() {
        return datePicker.getValue();
    }

    public void selectDate(LocalDate date) {
        datePicker.setValue(date);
    }

    @Override
    public HistoricalExchangeRatesRequest submit() throws InvalidSheetInputException {
        LocalDate selectedDate = getSelectedDate();

        if (Objects.isNull(selectedDate)) {
            throw new InvalidSheetInputException();
        }

        return new HistoricalExchangeRatesRequest(getSelectedBase(), getSelectedTargets(), selectedDate);
    }

    @Override
    public void load(HistoricalExchangeRatesRequest entity) {
        super.load(entity); /* No null-check: is already done by super */
        selectDate(entity.getDate());
    }

    @Override
    public void clear() {
        super.clear();
        selectDate(null);
    }
}
