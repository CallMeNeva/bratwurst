/*
 * Copyright 2022 Maxim Altoukhov
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

import com.altoukhov.frankfurterdesktop.gui.util.converter.CurrencyStringConverter;
import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.model.Exchange;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.function.Function;

public final class ExchangesView extends TableView<Exchange> {

    private static final StringConverter<Currency> CURRENCY_RENDERER = new CurrencyStringConverter();
    private static final StringConverter<Double> AMOUNT_RENDERER = new DoubleStringConverter();
    private static final StringConverter<LocalDate> DATE_RENDERER = new LocalDateStringConverter(FormatStyle.LONG);

    private static final String BASE_CURRENCY_COLUMN_NAME = "Base Currency";
    private static final String BASE_AMOUNT_COLUMN_NAME = "Base Amount";
    private static final String TARGET_CURRENCY_COLUMN_NAME = "Target Currency";
    private static final String TARGET_AMOUNT_COLUMN_NAME = "Target Amount";
    private static final String DATE_VALUE_COLUMN_NAME = "Date";

    public ExchangesView() {
        List<TableColumn<Exchange, ?>> columns = getColumns();
        columns.add(createColumn(BASE_CURRENCY_COLUMN_NAME, Exchange::base, CURRENCY_RENDERER));
        columns.add(createColumn(BASE_AMOUNT_COLUMN_NAME, Exchange::baseAmount, AMOUNT_RENDERER));
        columns.add(createColumn(TARGET_CURRENCY_COLUMN_NAME, Exchange::target, CURRENCY_RENDERER));
        columns.add(createColumn(TARGET_AMOUNT_COLUMN_NAME, Exchange::targetAmount, AMOUNT_RENDERER));
        columns.add(createColumn(DATE_VALUE_COLUMN_NAME, Exchange::date, DATE_RENDERER));

        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }

    private <T> TableColumn<Exchange, T> createColumn(String name, Function<Exchange, T> extractor, StringConverter<T> renderer) {
        TableColumn<Exchange, T> column = new TableColumn<>(name);

        column.setCellValueFactory(param -> {
            Exchange exchange = param.getValue();
            T exchangeProperty = extractor.apply(exchange);
            return new SimpleObjectProperty<>(exchangeProperty);
        });

        column.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(null);
                setText(empty ? null : renderer.toString(item));
            }
        });

        return column;
    }
}
