// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.dialog;

import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.scene.control.Alert;

public final class AlertFactory {

    private AlertFactory() {}

    public static Alert create(Alert.AlertType type, String l10nKey) {
        String content = Localization.getString(l10nKey);
        Alert alert = new Alert(type, content);
        alert.setHeaderText(null);
        return alert;
    }
}
