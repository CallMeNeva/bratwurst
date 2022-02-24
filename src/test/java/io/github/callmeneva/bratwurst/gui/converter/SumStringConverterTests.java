// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.converter;

import io.github.callmeneva.bratwurst.model.Sum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SumStringConverter tests")
class SumStringConverterTests {

    @Test
    @DisplayName("SumStringConverter::toString serializes non-null correctly")
    void serializes() {
        SumStringConverter converter = new SumStringConverter();
        Sum sum = new Sum("USD", 2.5);

        String expected = "2.5 USD";
        String actual = converter.toString(sum);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("SumStringConverter::fromString deserializes non-null correctly")
    void deserializes() {
        SumStringConverter converter = new SumStringConverter();
        String s = "3.4981 EUR";

        Sum expected = new Sum("EUR", 3.4981);
        Sum actual = converter.fromString(s);

        Assertions.assertEquals(expected, actual);
    }
}
