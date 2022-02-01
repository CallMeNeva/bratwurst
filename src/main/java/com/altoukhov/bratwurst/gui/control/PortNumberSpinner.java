// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.control;

import com.altoukhov.bratwurst.gui.converter.PortNumberStringConverter;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;
import org.apache.hc.core5.net.Ports;

public class PortNumberSpinner extends Spinner<Integer> {

    private static final StringConverter<Integer> DEFAULT_STRING_CONVERTER = new PortNumberStringConverter();

    public PortNumberSpinner(int port) {
        // NOTE: No validation needed on "port" as spinner rounds externally set values within bounds (not sure though)
        // NOTE: Since the string converter is set *after* the initial value is, if it's the default port value it won't be converted until the next
        // user input. This can be solved by simply setting the initial value to something else or at least setting it after the converter was.
        super(Ports.SCHEME_DEFAULT, Ports.MAX_VALUE, port, 1);
        SpinnerValueFactory<Integer> valueFactory = getValueFactory();
        valueFactory.setConverter(DEFAULT_STRING_CONVERTER);
    }

    public PortNumberSpinner() {
        this(0);
    }
}
