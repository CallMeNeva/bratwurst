// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.dialog;

import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.scene.control.Alert;

public final class AlertFactory {

    private AlertFactory() {}

    public static Alert create(Alert.AlertType type, String contentL10nPropertyName) {
        String content = Localization.get(contentL10nPropertyName);
        Alert alert = new Alert(type, content);
        alert.setHeaderText(null);
        return alert;
    }
}
