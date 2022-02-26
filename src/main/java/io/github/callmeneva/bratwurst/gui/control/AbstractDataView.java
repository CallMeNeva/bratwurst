// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

import java.util.function.Function;

public abstract class AbstractDataView<T> extends TableView<T> {

    protected AbstractDataView() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }

    protected final <P> void appendColumn(String l10nPropertyName, Function<T, P> propertyExtractor, StringConverter<P> customPropertyConverter) {
        String header = Localization.get(l10nPropertyName);
        TableColumn<T, P> column = new TableColumn<>(header);

        column.setCellValueFactory(parameter -> {
            T entity = parameter.getValue();
            P property = propertyExtractor.apply(entity);
            return new SimpleObjectProperty<>(property);
        });

        if (customPropertyConverter != null) {
            column.setCellFactory(param -> new TableCell<>() {
                @Override
                protected void updateItem(P item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(null); // Is this actually needed in this case?
                    setText(customPropertyConverter.toString(item));
                }
            });
        }

        ObservableList<TableColumn<T, ?>> columns = getColumns();
        columns.add(column);
    }

    protected final <P> void appendColumn(String l10nPropertyName, Function<T, P> propertyExtractor) {
        appendColumn(l10nPropertyName, propertyExtractor, null);
    }
}
