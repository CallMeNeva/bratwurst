// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Base class for entity sheets. Bluntly put, this is simply a panel with a bunch of vertically stacked property editors that can be used to
 * edit an entity's properties, where each editor has a label next to it with a display name for that property.
 *
 * @param <E> type of the entity
 */
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

    /**
     * Submits the current edits, creating a new entity.
     *
     * @return a new entity (not null)
     * @throws InvalidSheetInputException if the entity cannot be submitted because the editors contain invalid input
     */
    public abstract E submit() throws InvalidSheetInputException;

    public abstract void load(E entity);

    public abstract void clear();

    /**
     * Appends a row with a property editor and its label to the bottom of the panel. Subclasses must call this manually on each editor they
     * want to display. This method returns the editor that is appended so subclasses can create and append an editor in a single line, such
     * as:
     * <p>
     * <code>MyCustomEditor editor = appendEditor("Hello", MyCustomEditor::new);</code>
     * <p>
     * Label text is suffixed with a colon. If provided label text is null, some padding is added instead.
     *
     * @param labelText      the text to be displayed next to the editor (may be null)
     * @param editorSupplier an editor supplier (not null)
     * @param <C>            type of the editor
     * @return the appended editor
     * @throws NullPointerException if the supplied editor or the supplier itself is null
     */
    protected final <C extends Control> C appendEditor(String labelText, Supplier<C> editorSupplier) {
        Objects.requireNonNull(editorSupplier, "Editor supplier is null");
        C editor = Objects.requireNonNull(editorSupplier.get(), "Supplied editor is null");

        /*
         * HACK: I'm pretty sure column layout in this case should be done via ColumnConstraints, but seeing as how it doesn't work I don't
         * think I'm using the thing correctly, so this will do the trick instead.
         */
        editor.setMaxWidth(EDITOR_COLUMN_MAX_WIDTH);

        Node leftColumnNode = Objects.isNull(labelText) ? new Region() : new Label(labelText + ":");
        addRow(++lastRowIndex, leftColumnNode, editor);
        return editor;
    }
}
