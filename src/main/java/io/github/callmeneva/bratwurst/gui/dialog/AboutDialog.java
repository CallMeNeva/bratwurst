// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.dialog;

import io.github.callmeneva.bratwurst.gui.util.Icons;
import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;

public class AboutDialog extends Dialog<ButtonType> {

    public AboutDialog() {
        DialogPane pane = getDialogPane();

        pane.setHeaderText(null);
        pane.setContentText(Localization.get("dialog.about.content"));
        pane.setGraphic(new ImageView(Icons.PX_64));
        pane.getButtonTypes().setAll(ButtonType.OK);
    }
}
