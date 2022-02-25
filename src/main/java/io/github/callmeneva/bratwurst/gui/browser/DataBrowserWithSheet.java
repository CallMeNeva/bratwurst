// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.browser;

import io.github.callmeneva.bratwurst.gui.control.ExchangeDataView;
import io.github.callmeneva.bratwurst.gui.sheet.AbstractEntitySheet;
import io.github.callmeneva.bratwurst.gui.sheet.HistoricalExchangeDataRequestSheet;
import io.github.callmeneva.bratwurst.gui.sheet.LatestExchangeDataRequestSheet;
import io.github.callmeneva.bratwurst.gui.sheet.TimeSeriesExchangeDataRequestSheet;
import io.github.callmeneva.bratwurst.gui.util.Resettable;
import io.github.callmeneva.bratwurst.model.Exchange;
import io.github.callmeneva.bratwurst.service.DataFetcher;
import io.github.callmeneva.bratwurst.service.request.DataRequest;
import io.github.callmeneva.bratwurst.service.request.HistoricalExchangeDataRequest;
import io.github.callmeneva.bratwurst.service.request.LatestExchangeDataRequest;
import io.github.callmeneva.bratwurst.service.request.TimeSeriesExchangeDataRequest;
import javafx.scene.control.TableView;

import java.util.Objects;
import java.util.Set;

public final class DataBrowserWithSheet<T, R extends DataRequest> extends AbstractDataBrowser<T, R> implements Resettable {

    private final AbstractEntitySheet<R> requestSheet;

    private DataBrowserWithSheet(DataFetcher<Set<T>, R, ?> fetcher, TableView<T> dataView, AbstractEntitySheet<R> requestSheet) {
        super(fetcher, dataView);
        this.requestSheet = Objects.requireNonNull(requestSheet, "Browser request sheet must not be null");
        setRight(requestSheet);
    }

    public static DataBrowserWithSheet<Exchange, LatestExchangeDataRequest> forLatestExchanges() {
        return new DataBrowserWithSheet<>(DataFetcher.ofLatestExchanges(), new ExchangeDataView(), new LatestExchangeDataRequestSheet());
    }

    public static DataBrowserWithSheet<Exchange, HistoricalExchangeDataRequest> forHistoricalExchanges() {
        return new DataBrowserWithSheet<>(DataFetcher.ofHistoricalExchanges(), new ExchangeDataView(), new HistoricalExchangeDataRequestSheet());
    }

    public static DataBrowserWithSheet<Exchange, TimeSeriesExchangeDataRequest> forTimeSeriesExchanges() {
        return new DataBrowserWithSheet<>(DataFetcher.ofTimeSeriesExchanges(), new ExchangeDataView(), new TimeSeriesExchangeDataRequestSheet());
    }

    @Override
    protected R createRequest() {
        return requestSheet.submit();
    }

    @Override
    public void reset() {
        requestSheet.reset();
    }
}
