/*
 * Copyright 2021, 2022 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
