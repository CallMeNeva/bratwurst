// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.browser;

import io.github.callmeneva.bratwurst.gui.sheet.AbstractEntitySheet;
import io.github.callmeneva.bratwurst.gui.util.Resettable;
import io.github.callmeneva.bratwurst.service.DataFetchFailureException;
import io.github.callmeneva.bratwurst.service.DataFetcher;
import io.github.callmeneva.bratwurst.service.request.AbstractDataRequest;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.apache.commons.lang3.Validate;

import java.util.Set;

public class DataBrowser<T, R extends AbstractDataRequest> extends BorderPane implements Resettable {

    private final DataFetcher<Set<T>, R> dataFetcher;
    private final AbstractEntitySheet<R> requestSheet;
    private final TableView<T> dataView;

    public DataBrowser(DataFetcher<Set<T>, R> dataFetcher, AbstractEntitySheet<R> requestSheet, TableView<T> dataView) {
        this.dataFetcher = Validate.notNull(dataFetcher);
        this.requestSheet = Validate.notNull(requestSheet);
        this.dataView = Validate.notNull(dataView);

        setCenter(dataView);
        setRight(requestSheet);
    }

    public void submitRequest() throws DataFetchFailureException {
        R request = requestSheet.submit();
        Set<T> dataset = dataFetcher.fetch(request);

        ObservableList<T> dataViewItems = dataView.getItems();
        dataViewItems.setAll(dataset);
    }

    @Override
    public void reset() {
        requestSheet.reset();
    }
}
