// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.control;

import com.altoukhov.bratwurst.gui.util.converter.CurrencyStringConverter;
import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.model.Exchange;
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

    /*
     * TODO: Externalize UI strings
     */

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
