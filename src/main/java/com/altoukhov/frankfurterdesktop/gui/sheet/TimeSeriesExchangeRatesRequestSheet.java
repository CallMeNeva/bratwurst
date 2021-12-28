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

import com.altoukhov.frankfurterdesktop.service.request.TimeSeriesExchangeRatesRequest;
import javafx.scene.control.DatePicker;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.Validator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public final class TimeSeriesExchangeRatesRequestSheet extends AbstractExchangeRatesRequestSheet<TimeSeriesExchangeRatesRequest> {

    private static final String START_DATE_PICKER_LABEL = "Start date";
    private static final String INVALID_START_DATE_SELECTION_MESSAGE = "A valid start date must be specified";
    private static final String END_DATE_PICKER_LABEL = "End date";
    private static final String INVALID_END_DATE_SELECTION_MESSAGE = "A valid end date must be specified";
    private static final String NO_END_DATE_SELECTION_MESSAGE = "Current date will be used by default if not explicitly specified";

    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;

    public TimeSeriesExchangeRatesRequestSheet() {
        startDatePicker = appendDatePicker(
                START_DATE_PICKER_LABEL,
                Validator.createPredicateValidator(date -> rangeValid(date, getSelectedEndDate()), INVALID_START_DATE_SELECTION_MESSAGE)
        );

        endDatePicker = appendDatePicker(
                END_DATE_PICKER_LABEL,
                Validator.createEmptyValidator(NO_END_DATE_SELECTION_MESSAGE, Severity.WARNING)
        );
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
    public TimeSeriesExchangeRatesRequest submit() throws InvalidSheetInputException {
        LocalDate startDate = getSelectedStartDate();
        LocalDate endDate = getSelectedEndDate();

        if (!rangeValid(startDate, endDate)) {
            throw new InvalidSheetInputException();
        }

        return new TimeSeriesExchangeRatesRequest(getSelectedBase(), getSelectedTargets(), startDate, endDate);
    }

    @Override
    public void load(TimeSeriesExchangeRatesRequest entity) {
        super.load(entity); /* No null-check: is already done by super */
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
