// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.util.Resettable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MultiCurrencyCodeField extends FilteredTextField implements Resettable {

    private static final String DELIMITER = ",";
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" + DELIMITER; // FIXME: wtf is this?
    private static final String HINT = String.join(DELIMITER, "USD", "GBP", "CHF");

    public MultiCurrencyCodeField() {
        super(text -> StringUtils.isEmpty(text) || StringUtils.containsOnly(text, ALLOWED_CHARACTERS));
        setPromptText(HINT);
    }

    public List<String> getCodes() {
        String[] codes = StringUtils.splitByWholeSeparator(getText(), DELIMITER);

        return Arrays.asList(ArrayUtils.nullToEmpty(codes));
    }

    public void setCodes(List<String> codes) {
        if (ObjectUtils.isEmpty(codes)) {
            clear();
        } else {
            String text = codes.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(DELIMITER));

            setText(text);
        }
    }

    @Override
    public void reset() {
        clear();
    }
}
