// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.control;

import com.altoukhov.bratwurst.gui.util.converter.PortNumberStringConverter;
import javafx.scene.control.Spinner;
import org.apache.hc.core5.net.Ports;

public final class PortNumberSpinner extends Spinner<Integer> {

    public PortNumberSpinner() {
        super(Ports.SCHEME_DEFAULT, Ports.MAX_VALUE, Ports.MIN_VALUE);
        getValueFactory().setConverter(new PortNumberStringConverter());
    }

    public PortNumberSpinner(int port) {
        this();
        setValue(port);
    }

    public void setValue(int port) {
        getValueFactory().setValue(port);
    }

    public void setDefaultValue() {
        getValueFactory().setValue(Ports.SCHEME_DEFAULT);
    }
}
