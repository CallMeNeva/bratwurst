// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

import io.github.callmeneva.bratwurst.service.request.CurrencyDataRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DataFetcher")
class DataFetcherTests {

    @Test
    @DisplayName("Throws when fetching with illegal host")
    public void illegalHost() {
        DataFetcher<?, CurrencyDataRequest, ?> fetcher = DataFetcher.ofCurrencies();
        fetcher.setHost("This is probably not a legal host.");

        Assertions.assertThrows(DataFetchFailureException.class, () -> fetcher.fetch(new CurrencyDataRequest()));
    }

    @Test
    @DisplayName("Throws when fetching with empty host")
    public void emptyHost() {
        DataFetcher<?, CurrencyDataRequest, ?> fetcher = DataFetcher.ofCurrencies();
        fetcher.setHost("");

        Assertions.assertThrows(DataFetchFailureException.class, () -> fetcher.fetch(new CurrencyDataRequest()));
    }

    @Test
    @DisplayName("Throws when fetching with blank host")
    public void blankHost() {
        DataFetcher<?, CurrencyDataRequest, ?> fetcher = DataFetcher.ofCurrencies();
        fetcher.setHost("\t \n \r");

        Assertions.assertThrows(DataFetchFailureException.class, () -> fetcher.fetch(new CurrencyDataRequest()));
    }

    @Test
    @DisplayName("Throws when fetching with null host")
    public void nullHost() {
        DataFetcher<?, CurrencyDataRequest, ?> fetcher = DataFetcher.ofCurrencies();
        fetcher.setHost(null);

        Assertions.assertThrows(DataFetchFailureException.class, () -> fetcher.fetch(new CurrencyDataRequest()));
    }
}
