/*
 * Copyright 2021, 2022 Maxim Altoukhov
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

import com.altoukhov.frankfurterdesktop.service.request.HistoricalExchangeDataRequest;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.Objects;

public final class HistoricalExchangesRequestSheet extends AbstractExchangesRequestSheet<HistoricalExchangeDataRequest> {

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
