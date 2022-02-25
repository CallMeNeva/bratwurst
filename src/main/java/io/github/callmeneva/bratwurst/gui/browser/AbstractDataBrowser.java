// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.browser;

import io.github.callmeneva.bratwurst.service.DataFetchFailureException;
import io.github.callmeneva.bratwurst.service.DataFetcher;
import io.github.callmeneva.bratwurst.service.request.DataRequest;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.util.Objects;
import java.util.Set;

public abstract class AbstractDataBrowser<T, R extends DataRequest> extends BorderPane {

    private final DataFetcher<Set<T>, R, ?> fetcher;
    private final TableView<T> dataView;

    protected AbstractDataBrowser(DataFetcher<Set<T>, R, ?> fetcher, TableView<T> dataView) {
        this.fetcher = Objects.requireNonNull(fetcher, "Browser fetcher must not be null");
        this.dataView = Objects.requireNonNull(dataView, "Browser data view must not be null");
        setCenter(dataView);
    }

    protected abstract R createRequest();

    public final DataFetcher<Set<T>, R, ?> getFetcher() {
        return fetcher;
    }

    public final void fetch() throws DataFetchFailureException {
        R request = createRequest();
        Set<T> dataset = fetcher.fetch(request);
        dataView.getItems().setAll(dataset);
    }
}
