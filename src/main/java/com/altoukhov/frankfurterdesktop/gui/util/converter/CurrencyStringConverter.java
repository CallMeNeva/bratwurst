// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.gui.util.converter;

import com.altoukhov.frankfurterdesktop.model.Currency;
import javafx.util.StringConverter;

import java.util.Objects;

public final class CurrencyStringConverter extends StringConverter<Currency> {

    @Override
    public String toString(Currency object) {
        return Objects.isNull(object) ? null : object.displayName();
    }

    @Override
    public Currency fromString(String s) {
        throw new UnsupportedOperationException("Converting string to currency is not supported");
    }
}
