// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.converter;

import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.model.Sum;
import io.github.callmeneva.bratwurst.util.Arguments;
import javafx.util.StringConverter;

public class SumStringConverter extends StringConverter<Sum> {

    @Override
    public String toString(Sum sum) {
        return Arguments.nullOrElseApply(sum, nonNullSum -> {
            Currency currency = nonNullSum.currency();
            return 'x' + nonNullSum.amount() + ' ' + currency.name();
        });
    }

    @Override
    public Sum fromString(String string) {
        throw new UnsupportedOperationException("Converting string to sum is not supported");
    }
}
