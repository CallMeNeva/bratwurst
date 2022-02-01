// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.converter;

import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.model.Sum;
import com.altoukhov.bratwurst.util.Arguments;
import javafx.util.StringConverter;

public final class SumStringConverter extends StringConverter<Sum> {

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
