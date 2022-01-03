/*
 * Copyright 2022 Maxim Altoukhov
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

import javafx.util.converter.IntegerStringConverter;
import org.apache.hc.core5.net.Ports;

import java.util.Objects;

public class PortNumberStringConverter extends IntegerStringConverter {

    private static final String DEFAULT_PORT_TEXT = "Default";

    @Override
    public String toString(Integer value) {
        return Objects.equals(value, Ports.SCHEME_DEFAULT) ? DEFAULT_PORT_TEXT : super.toString(value);
    }

    @Override
    public Integer fromString(String value) {
        return Objects.equals(value, DEFAULT_PORT_TEXT) ? Ports.SCHEME_DEFAULT : super.fromString(value);
    }
}
