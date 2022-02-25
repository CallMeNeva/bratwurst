// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.menu;

import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public final class MenuItemFactory {

    private MenuItemFactory() {}

    public static MenuItem create(String l10nPropertyName, EventHandler<ActionEvent> onAction) {
        String text = Localization.get(l10nPropertyName);
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(onAction);
        return menuItem;
    }
}
