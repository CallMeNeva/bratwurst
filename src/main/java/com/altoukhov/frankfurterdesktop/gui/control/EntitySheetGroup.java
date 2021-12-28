/*
 * Copyright 2021 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.altoukhov.frankfurterdesktop.gui.control;

import com.altoukhov.frankfurterdesktop.gui.sheet.AbstractEntitySheet;
import com.altoukhov.frankfurterdesktop.gui.sheet.InvalidSheetInputException;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Objects;

public final class EntitySheetGroup extends TabPane {

    public EntitySheetGroup() {
        super();
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

    public <E> E submitFromSelectedSheetAs(Class<E> entityClass) throws InvalidSheetInputException {
        Objects.requireNonNull(entityClass, "Provided entity class is null");
        AbstractEntitySheet<?> sheet = getSelectedSheet();
        return entityClass.cast(sheet.submit());
    }

    public void clearSelectedSheet() {
        getSelectedSheet().clear();
    }
}
