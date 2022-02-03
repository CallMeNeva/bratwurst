// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.converter.SumStringConverter;
import io.github.callmeneva.bratwurst.l10n.Localization;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.model.Sum;
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

public class ExchangeDataView extends TableView<Exchange> {

    private static final String COMMITMENT_COLUMN_TITLE = Localization.getString("exchange-view.commitment-column-title");
    private static final String RESULT_COLUMN_TITLE = Localization.getString("exchange-view.result-column-title");
    private static final String DATE_COLUMN_TITLE = Localization.getString("exchange-view.date-column-title");
    private static final String NO_CONTENT_TEXT = Localization.getString("exchange-view.no-content-text");

    private static final StringConverter<Sum> DEFAULT_SUM_STRING_CONVERTER = new SumStringConverter();
    private static final StringConverter<LocalDate> DEFAULT_DATE_STRING_CONVERTER = new LocalDateStringConverter(FormatStyle.LONG);

    public ExchangeDataView() {
        List<TableColumn<Exchange, ?>> columns = getColumns();

        TableColumn<Exchange, Sum> commitmentColumn = createColumn(COMMITMENT_COLUMN_TITLE, Exchange::commitment, DEFAULT_SUM_STRING_CONVERTER);
        columns.add(commitmentColumn);

        TableColumn<Exchange, Sum> resultColumn = createColumn(RESULT_COLUMN_TITLE, Exchange::result, DEFAULT_SUM_STRING_CONVERTER);
        columns.add(resultColumn);

        TableColumn<Exchange, LocalDate> dateColumn = createColumn(DATE_COLUMN_TITLE, Exchange::date, DEFAULT_DATE_STRING_CONVERTER);
        columns.add(dateColumn);

        setPlaceholder(new Label(NO_CONTENT_TEXT));
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }

    private <T> TableColumn<Exchange, T> createColumn(String title, Function<Exchange, T> propertyExtractor, StringConverter<T> propertyRenderer) {
        TableColumn<Exchange, T> column = new TableColumn<>(title);

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
