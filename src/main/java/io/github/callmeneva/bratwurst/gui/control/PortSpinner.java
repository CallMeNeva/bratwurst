// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.converter.PortStringConverter;
import io.github.callmeneva.bratwurst.gui.util.Resettable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.apache.hc.core5.net.Ports;

public class PortSpinner extends Spinner<Integer> implements Resettable {

    public PortSpinner(int port) {
        super(Ports.SCHEME_DEFAULT, Ports.MAX_VALUE, Ports.MIN_VALUE, 1);

        SpinnerValueFactory<Integer> valueFactory = getValueFactory();
        valueFactory.setConverter(new PortStringConverter());
        valueFactory.setValue(port); // Set the initial value *after* the converter to ensure it gets converted even if it's the scheme default
    }

    public PortSpinner() {
        this(Ports.SCHEME_DEFAULT);
    }

    @Override
    public void reset() {
        getValueFactory().setValue(Ports.SCHEME_DEFAULT);
    }
}
