// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.util.Resettable;

import java.util.regex.Pattern;

public class SingleCurrencyCodeTextField extends AbstractFilteredTextField implements Resettable {

    private static final Pattern PATTERN = Pattern.compile("[A-Z]{0,3}");
    private static final String HINT = "EUR";

    public SingleCurrencyCodeTextField() {
        setPromptText(HINT);
    }

    public String getCode() {
        String text = getText();
        return (text == null) || text.isEmpty() ? null : text;
    }

    @Override
    public void reset() {
        clear();
    }

    @Override
    protected boolean isValid(String text) {
        return (text == null) || PATTERN.matcher(text).matches();
    }
}
