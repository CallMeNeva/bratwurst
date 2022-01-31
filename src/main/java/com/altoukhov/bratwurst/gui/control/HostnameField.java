// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.control;

import javafx.scene.control.TextField;

public final class HostnameField extends TextField {

    private static final String PROMPT_TEXT = "example.com";

    public HostnameField() {
        setPromptText(PROMPT_TEXT);
    }
}
