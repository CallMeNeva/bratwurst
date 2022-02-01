// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import com.altoukhov.bratwurst.util.Arguments;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public abstract class AbstractEntitySheet<E> extends GridPane {

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

    public abstract E submit() throws InvalidSheetInputException;

    public abstract void load(E entity);

    public abstract void clear();

    protected final <C extends Control> C appendEditor(String labelText, C editor) {
        Arguments.checkNull(editor, "editor");
        // I'm pretty sure column layout in this case should be done via ColumnConstraints, but seeing as how it doesn't work I don't think I'm using
        // the thing correctly, so this will do the trick instead.
        editor.setMaxWidth(EDITOR_COLUMN_MAX_WIDTH);

        Node leftColumnNode = (labelText != null) ? new Label(labelText + ":") : new Region();
        addRow(++lastRowIndex, leftColumnNode, editor);
        return editor;
    }
}
