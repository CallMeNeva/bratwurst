// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.util.Resettable;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class MultiCurrencyCodeTextField extends AbstractFilteredTextField implements Resettable {

    private static final String DELIMITER = ",";
    private static final String SPLIT_PATTERN = Pattern.quote(DELIMITER);
    private static final Pattern FILTER_PATTERN = Pattern.compile(""); // TODO
    private static final String HINT = String.join(DELIMITER, "USD", "GBP", "CHF");

    public MultiCurrencyCodeTextField() {
        setPromptText(HINT);
    }

    public List<String> getCodes() {
        String text = getText();

        if ((text == null) || text.isEmpty()) {
            return List.of();
        }

        String[] codes = text.split(SPLIT_PATTERN);
        return Arrays.asList(codes);
    }

    @Override
    public void reset() {
        clear();
    }

    @Override
    protected boolean isValid(String text) {
        return (text == null) || FILTER_PATTERN.matcher(text).matches();
    }
}
