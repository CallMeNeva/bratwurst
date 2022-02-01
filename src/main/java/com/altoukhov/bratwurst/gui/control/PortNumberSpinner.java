// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.control;

import com.altoukhov.bratwurst.gui.converter.PortNumberStringConverter;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.apache.hc.core5.net.Ports;

public final class PortNumberSpinner extends Spinner<Integer> {

    private static final PortNumberStringConverter STRING_CONVERTER = new PortNumberStringConverter();

    public PortNumberSpinner(int port) {
        // No validation needed on "port" as spinner rounds externally set values within bounds (not sure though)
        super(Ports.SCHEME_DEFAULT, Ports.MAX_VALUE, port, 1);
        SpinnerValueFactory<Integer> valueFactory = getValueFactory();
        valueFactory.setConverter(STRING_CONVERTER);
    }

    public PortNumberSpinner() {
        this(Ports.SCHEME_DEFAULT);
    }
}
