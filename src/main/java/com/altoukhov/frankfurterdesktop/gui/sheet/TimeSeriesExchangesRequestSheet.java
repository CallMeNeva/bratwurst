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

import com.altoukhov.frankfurterdesktop.service.request.TimeSeriesExchangeDataRequest;
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
