// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import javafx.scene.control.TextField;

public class HostnameField extends TextField {

    private static final String PROMPT_TEXT = "example.com";

    public HostnameField() {
        setPromptText(PROMPT_TEXT);
        // TODO: Some validation/formatting maybe?
    }
}
