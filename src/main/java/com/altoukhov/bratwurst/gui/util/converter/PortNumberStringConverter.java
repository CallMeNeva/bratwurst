// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.util.converter;

import javafx.util.converter.IntegerStringConverter;
import org.apache.hc.core5.net.Ports;

import java.util.Objects;

public class PortNumberStringConverter extends IntegerStringConverter {

    /*
     * TODO: Externalize UI strings
     */

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
