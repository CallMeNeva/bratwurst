// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.action;

import io.github.callmeneva.bratwurst.gui.dialog.AboutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Dialog;
import org.apache.commons.lang3.Validate;

public class DialogDisplayAction implements EventHandler<ActionEvent> {

    private final Dialog<?> dialog;

    public DialogDisplayAction(Dialog<?> dialog) {
        this.dialog = Validate.notNull(dialog);
    }

    public static DialogDisplayAction withAboutDialog() {
        return new DialogDisplayAction(new AboutDialog());
    }

    @Override
    public void handle(ActionEvent event) {
        dialog.showAndWait();
        event.consume();
    }
}
