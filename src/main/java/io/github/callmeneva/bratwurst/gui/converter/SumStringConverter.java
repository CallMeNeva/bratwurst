// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.converter;

import io.github.callmeneva.bratwurst.model.Sum;
import javafx.util.StringConverter;

public class SumStringConverter extends StringConverter<Sum> {

    private static final String DELIMITER = " ";

    @Override
    public String toString(Sum sum) {
        return (sum == null) ? "" : (sum.amount() + DELIMITER + sum.currencyCode());
    }

    @Override
    public Sum fromString(String string) {
        throw new UnsupportedOperationException();
    }
}
