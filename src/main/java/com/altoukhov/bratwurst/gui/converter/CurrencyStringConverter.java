// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.converter;

import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.util.Arguments;
import javafx.util.StringConverter;

public class CurrencyStringConverter extends StringConverter<Currency> {

    @Override
    public String toString(Currency currency) {
        return Arguments.nullOrElseApply(currency, Currency::name);
    }

    @Override
    public Currency fromString(String string) {
        throw new UnsupportedOperationException("Converting string to currency is not supported");
    }
}
