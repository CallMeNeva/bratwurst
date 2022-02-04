// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.dialog;

import io.github.callmeneva.bratwurst.gui.util.Icons;
import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;

public class AboutDialog extends Dialog<ButtonType> {

    public AboutDialog() {
        DialogPane pane = getDialogPane();
        pane.setHeaderText(null);
        pane.setContentText(Localization.getString("dialog.about.content"));
        pane.setGraphic(new ImageView(Icons.PX_64));

        ObservableList<ButtonType> buttonTypes = pane.getButtonTypes();
        buttonTypes.setAll(ButtonType.OK);
    }
}
