// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.control;

import com.altoukhov.bratwurst.gui.sheet.AbstractEntitySheet;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public final class EntitySheetGroup extends TabPane {

    public EntitySheetGroup() {
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    public void addSheet(String name, AbstractEntitySheet<?> sheet) {
        Tab sheetTab = new Tab(name, sheet);
        getTabs().add(sheetTab);
    }

    public AbstractEntitySheet<?> getSelectedSheet() {
        Tab selectedTab = getSelectionModel().getSelectedItem();
        Node tabContent = selectedTab.getContent();
        if (tabContent instanceof AbstractEntitySheet<?> sheet) {
            return sheet;
        }
        throw new IllegalStateException("Tab content is not an AbstractEntitySheet");
    }

    public void clearSelectedSheet() {
        getSelectedSheet().clear();
    }
}
