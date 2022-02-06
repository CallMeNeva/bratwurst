// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.menu;

import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public final class MenuFactory {

    private MenuFactory() {}

    public static Menu create(String l10nKey, MenuItem... items) {
        String text = Localization.getString(l10nKey);
        return new Menu(text, null, items);
    }
}
