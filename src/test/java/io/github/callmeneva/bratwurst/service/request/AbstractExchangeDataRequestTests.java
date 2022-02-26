// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@DisplayName("AbstractExchangeDataRequest")
class AbstractExchangeDataRequestTests {

    private static class DummyExchangeDataRequest extends AbstractExchangeDataRequest {

        public DummyExchangeDataRequest(String baseCurrencyCode, List<String> targetCurrencyCodes, double amount) {
            super(baseCurrencyCode, targetCurrencyCodes, amount);
        }

        @Override
        public String getEndpointName() {
            return "hello";
        }
    }

    @Test
    @DisplayName("Parameter serialization handles null base and null targets")
    public void nullBaseNullTargets() {
        DummyExchangeDataRequest request = new DummyExchangeDataRequest(null, null, 0.0);

        Map<String, String> expected = Map.of("amount", "0.0");
        Map<String, String> actual = request.getParameters();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Parameter serialization handles null base and non-null targets")
    public void nullBaseNonNullTargets() {
        DummyExchangeDataRequest request = new DummyExchangeDataRequest(null, List.of("USD", "EUR", "CHF"), 1.0);

        Map<String, String> expected = Map.of("amount", "1.0", "to", "USD,EUR,CHF");
        Map<String, String> actual = request.getParameters();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Parameter serialization handles non-null base and null targets")
    public void nonNullBaseNullTargets() {
        DummyExchangeDataRequest request = new DummyExchangeDataRequest("EUR", null, 2.0);

        Map<String, String> expected = Map.of("amount", "2.0", "from", "EUR");
        Map<String, String> actual = request.getParameters();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Parameter serialization handles non-null base and non-null targets")
    public void nonNullBaseNonNullTargets() {
        DummyExchangeDataRequest request = new DummyExchangeDataRequest("JPY", List.of("GBP", "AUD"), 3.0);

        Map<String, String> expected = Map.of("amount", "3.0", "from", "JPY", "to", "GBP,AUD");
        Map<String, String> actual = request.getParameters();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Parameter serialization handles empty targets")
    public void emptyTargets() {
        DummyExchangeDataRequest request = new DummyExchangeDataRequest("NOK", List.of(), 4.0);

        Map<String, String> expected = Map.of("amount", "4.0", "from", "NOK");
        Map<String, String> actual = request.getParameters();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Parameter serialization handles single target")
    public void singleTarget() {
        DummyExchangeDataRequest request = new DummyExchangeDataRequest("KRW", List.of("DKK"), 5.0);

        Map<String, String> expected = Map.of("amount", "5.0", "from", "KRW", "to", "DKK");
        Map<String, String> actual = request.getParameters();

        Assertions.assertEquals(expected, actual);
    }
}
