// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

@DisplayName("DataRequest")
class DataRequestTests {

    @Test
    @DisplayName("URI path conversion handles multiple parameters")
    public void multipleParametersPathForm() {
        DataRequest request = new DataRequest() {
            @Override
            public String getEndpointName() {
                return "test";
            }

            @Override
            public Map<String, String> getParameters() {
                return Map.of("hello", "world", "foo", "bar");
            }
        };

        String expected1 = "/test?hello=world&foo=bar";
        String expected2 = "/test?foo=bar&hello=world";
        String actual = request.toPathForm();

        Assertions.assertThat(actual).isIn(expected1, expected2);
    }

    @Test
    @DisplayName("URI path conversion handles single parameter")
    public void singleParameterPathForm() {
        DataRequest request = new DataRequest() {
            @Override
            public String getEndpointName() {
                return "test";
            }

            @Override
            public Map<String, String> getParameters() {
                return Map.of("hello", "world");
            }
        };

        String expected = "/test?hello=world";
        String actual = request.toPathForm();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("URI path conversion handles no parameters")
    public void noParametersPathForm() {
        DataRequest request = () -> "test";

        String expected = "/test";
        String actual = request.toPathForm();

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
