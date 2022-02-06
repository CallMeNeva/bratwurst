// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.util.Resettable;
import javafx.scene.control.DatePicker;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;
import java.util.function.Supplier;

public class DefaultableDatePicker extends DatePicker implements Resettable {

    private final Supplier<LocalDate> defaultValueSupplier;

    public DefaultableDatePicker(Supplier<LocalDate> defaultValueSupplier) {
        this.defaultValueSupplier = Validate.notNull(defaultValueSupplier);
        // FIXME: Handle external modifications
//        valueProperty().addListener(observable -> {
//            if (getValue() == null) {
//                reset();
//            }
//        });
        reset();
    }

    public DefaultableDatePicker(LocalDate defaultValue) {
        this(() -> defaultValue);
    }

    public DefaultableDatePicker() {
        this(() -> null);
    }

    @Override
    public void reset() {
        LocalDate defaultValue = defaultValueSupplier.get();
        setValue(defaultValue);
    }
}
