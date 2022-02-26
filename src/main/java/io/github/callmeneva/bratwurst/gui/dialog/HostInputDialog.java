// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.dialog;

import io.github.callmeneva.bratwurst.service.DataFetcher;
import javafx.scene.control.TextInputDialog;

public class HostInputDialog extends TextInputDialog {

    private static final String HINT = "example.com";

    public HostInputDialog() {
        super(DataFetcher.getDefaultHost());
        getEditor().setPromptText(HINT);
        setHeaderText(null);
    }
}
