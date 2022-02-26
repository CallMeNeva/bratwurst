// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.menu;

import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

import java.util.Objects;
import java.util.function.Consumer;

public final class MenuItemFactory {

    private MenuItemFactory() {}

    public static MenuItem createRegular(String l10nPropertyName, EventHandler<ActionEvent> onAction) {
        String text = Localization.get(l10nPropertyName);
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(onAction);
        return menuItem;
    }

    public static CheckMenuItem createCheckable(String l10nPropertyName, Consumer<Boolean> onCheck, boolean initialState) {
        Objects.requireNonNull(onCheck, "On-check handler must not be null");

        String text = Localization.get(l10nPropertyName);
        CheckMenuItem checkMenuItem = new CheckMenuItem(text);
        checkMenuItem.selectedProperty().addListener((observable, oldValue, newValue) -> onCheck.accept(newValue));
        checkMenuItem.setSelected(initialState);

        return checkMenuItem;
    }
}
