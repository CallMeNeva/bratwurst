// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.browser;

import io.github.callmeneva.bratwurst.gui.control.CurrencyDataView;
import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.service.DataFetcher;
import io.github.callmeneva.bratwurst.service.request.CurrencyDataRequest;
import io.github.callmeneva.bratwurst.service.request.DataRequest;
import javafx.scene.control.TableView;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public final class DataBrowserWithoutSheet<T, R extends DataRequest> extends AbstractDataBrowser<T, R> {

    private final Supplier<R> requestSupplier;

    public DataBrowserWithoutSheet(DataFetcher<Set<T>, R, ?> fetcher, TableView<T> dataView, Supplier<R> requestSupplier) {
        super(fetcher, dataView);
        this.requestSupplier = Objects.requireNonNull(requestSupplier, "Browser request supplier must not be null");
    }

    public static DataBrowserWithoutSheet<Currency, CurrencyDataRequest> forCurrencies() {
        return new DataBrowserWithoutSheet<>(DataFetcher.ofCurrencies(), new CurrencyDataView(), CurrencyDataRequest::new);
    }

    @Override
    protected R createRequest() {
        return requestSupplier.get();
    }
}
