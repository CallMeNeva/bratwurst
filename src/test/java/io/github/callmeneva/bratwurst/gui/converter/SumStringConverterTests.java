// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.converter;

import io.github.callmeneva.bratwurst.model.Sum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SumStringConverter")
class SumStringConverterTests {

    @Test
    @DisplayName("Correctly converts non-null to string")
    void convertsToString() {
        SumStringConverter converter = new SumStringConverter();
        Sum sum = new Sum("USD", 2.5);

        String expected = "2.5 USD";
        String actual = converter.toString(sum);

        Assertions.assertEquals(expected, actual);
    }
}
