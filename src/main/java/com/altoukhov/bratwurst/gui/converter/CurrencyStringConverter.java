// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.converter;

import com.altoukhov.bratwurst.model.Currency;
import javafx.util.StringConverter;

public final class CurrencyStringConverter extends StringConverter<Currency> {

    private final String onNullValue;

    public CurrencyStringConverter(String onNullValue) {
        this.onNullValue = onNullValue;
    }

    public CurrencyStringConverter() {
        this(null);
    }

    @Override
    public String toString(Currency currency) {
        return (currency != null) ? currency.name() : onNullValue;
    }

    @Override
    public Currency fromString(String string) {
        throw new UnsupportedOperationException("Converting string to currency is not supported");
    }
}
