// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public abstract class AbstractFilteredTextField extends TextField {

    protected AbstractFilteredTextField() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return isValid(newText) ? change : null;
        };

        setTextFormatter(new TextFormatter<>(filter));
    }

    protected abstract boolean isValid(String text);
}
