// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.apache.commons.lang3.Validate;

import java.util.function.Predicate;

public class FilteredTextField extends TextField {

    public FilteredTextField(Predicate<String> criteria) {
        Validate.notNull(criteria);
        setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return criteria.test(newText) ? change : null;
        }));
    }
}
