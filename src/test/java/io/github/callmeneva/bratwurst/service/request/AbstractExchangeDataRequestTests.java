// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import io.github.callmeneva.bratwurst.model.Currency;
import org.apache.hc.core5.http.HttpHost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DisplayName("AbstractExchangeDataRequest tests")
class AbstractExchangeDataRequestTests {

    private static final HttpHost DUMMY_HOST = new HttpHost("https", "example.com");
    private static final AbstractExchangeDataRequest REQUEST = new AbstractExchangeDataRequest(null, null, 0.0) {
        @Override
        public String getEndpointName() {
            return "hello";
        }
    };

    @Test
    @DisplayName("AbstractExchangeDataRequest::getParameters handles empty targets collection")
    public void handlesEmptyTargetsCollection() throws URISyntaxException {
        Collection<Currency> targets = List.of();
        REQUEST.setTargets(targets);

        URI expected = URI.create("https://example.com/hello?amount=0.0");
        URI actual = REQUEST.toURIWithHost(DUMMY_HOST);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("AbstractExchangeDataRequest::getParameters handles null targets collection")
    public void handlesNullTargetsCollection() throws URISyntaxException {
        REQUEST.setTargets(null);

        URI expected = URI.create("https://example.com/hello?amount=0.0");
        URI actual = REQUEST.toURIWithHost(DUMMY_HOST);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("AbstractExchangeDataRequest::getParameters handles null targets")
    public void handlesNullTargets() throws URISyntaxException {
        Collection<Currency> targets = new ArrayList<>();
        targets.add(null);
        REQUEST.setTargets(targets);

        URI expected = URI.create("https://example.com/hello?amount=0.0");
        URI actual = REQUEST.toURIWithHost(DUMMY_HOST);

        Assertions.assertEquals(expected, actual);
    }
}
