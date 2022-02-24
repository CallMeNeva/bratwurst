// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.gui.util.Resettable;
import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public abstract class AbstractEntitySheet<E> extends GridPane implements Resettable {

    private static final double VERTICAL_SPACING = 10.0;
    private static final double HORIZONTAL_SPACING = 15.0;
    private static final Insets OUTER_PADDING = new Insets(15.0);
    private static final double EDITOR_COLUMN_MAX_WIDTH = 150.0;

    private int lastRowIndex;

    protected AbstractEntitySheet() {
        lastRowIndex = -1;

        setVgap(VERTICAL_SPACING);
        setHgap(HORIZONTAL_SPACING);
        setPadding(OUTER_PADDING);
    }

    public abstract E submit();

    protected final <C extends Control> C appendEditor(String l10nKey, C editor) {
        // I'm pretty sure column layout in this case should be done via ColumnConstraints, but seeing as how it doesn't work I don't think I'm using
        // the thing correctly, so this will do the trick instead.
        editor.setMaxWidth(EDITOR_COLUMN_MAX_WIDTH);

        String labelText = Localization.getString(l10nKey);
        Label label = new Label(labelText + ':');
        addRow(++lastRowIndex, label, editor);
        return editor;
    }
}
