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

package com.altoukhov.frankfurterdesktop.gui.control;

import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.model.ExchangeRate;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.function.Function;

public final class ExchangeRatesView extends TableView<ExchangeRate> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    private static final String BASE_COLUMN_NAME = "Base";
    private static final String TARGET_COLUMN_NAME = "Target";
    private static final String RATE_VALUE_COLUMN_NAME = "Value";
    private static final String DATE_VALUE_COLUMN_NAME = "Date";

    public ExchangeRatesView() {
        List<TableColumn<ExchangeRate, ?>> columns = getColumns();
        columns.add(createColumn(BASE_COLUMN_NAME, ExchangeRate::base, Currency::displayName));
        columns.add(createColumn(TARGET_COLUMN_NAME, ExchangeRate::target, Currency::displayName));
        columns.add(createColumn(RATE_VALUE_COLUMN_NAME, ExchangeRate::value, BigDecimal::toPlainString));
        columns.add(createColumn(DATE_VALUE_COLUMN_NAME, ExchangeRate::date, DATE_FORMATTER::format));

        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }

    private <T> TableColumn<ExchangeRate, T> createColumn(String name, Function<ExchangeRate, T> extractor, Function<T, String> renderer) {
        TableColumn<ExchangeRate, T> column = new TableColumn<>(name);

        column.setCellValueFactory(param -> {
            ExchangeRate rate = param.getValue();
            T rateProperty = extractor.apply(rate);
            return new SimpleObjectProperty<>(rateProperty);
        });

        column.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(null);
                setText(empty ? null : renderer.apply(item));
            }
        });

        return column;
    }
}
