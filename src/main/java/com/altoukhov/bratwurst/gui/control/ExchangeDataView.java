// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.control;

import com.altoukhov.bratwurst.gui.util.converter.SumStringConverter;
import com.altoukhov.bratwurst.model.Exchange;
import com.altoukhov.bratwurst.model.Sum;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.function.Function;

public final class ExchangeDataView extends TableView<Exchange> {

    // FIXME: Externalize UI strings
    private static final String COMMITMENT_COLUMN_NAME = "Commitment";
    private static final String RESULT_COLUMN_NAME = "Result";
    private static final String DATE_COLUMN_NAME = "Date";
    private static final String PLACEHOLDER_TEXT = "Looks like it's pretty empty in here. Try sending a request!";

    private static final StringConverter<Sum> SUM_RENDERER = new SumStringConverter();
    private static final StringConverter<LocalDate> DATE_RENDERER = new LocalDateStringConverter(FormatStyle.LONG);

    public ExchangeDataView() {
        List<TableColumn<Exchange, ?>> columns = getColumns();
        columns.add(createColumn(COMMITMENT_COLUMN_NAME, Exchange::commitment, SUM_RENDERER));
        columns.add(createColumn(RESULT_COLUMN_NAME, Exchange::result, SUM_RENDERER));
        columns.add(createColumn(DATE_COLUMN_NAME, Exchange::date, DATE_RENDERER));

        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setPlaceholder(new Label(PLACEHOLDER_TEXT));
    }

    private <T> TableColumn<Exchange, T> createColumn(String name, Function<Exchange, T> propertyExtractor, StringConverter<T> propertyRenderer) {
        TableColumn<Exchange, T> column = new TableColumn<>(name);

        column.setCellValueFactory(param -> {
            Exchange exchange = param.getValue();
            T exchangeProperty = propertyExtractor.apply(exchange);
            return new SimpleObjectProperty<>(exchangeProperty);
        });

        column.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(null);
                setText(empty ? null : propertyRenderer.toString(item));
            }
        });

        return column;
    }
}
