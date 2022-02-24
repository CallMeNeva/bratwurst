// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.util.Resettable;
import org.apache.commons.lang3.StringUtils;

public class SingleCurrencyCodeField extends FilteredTextField implements Resettable {

    private static final String HINT = "EUR";

    public SingleCurrencyCodeField() {
        super(text -> StringUtils.isEmpty(text) || StringUtils.isAlpha(text));
        setPromptText(HINT);
    }

    @Override
    public void reset() {
        clear();
    }
}
