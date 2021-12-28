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

package com.altoukhov.frankfurterdesktop.gui.sheet;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.time.LocalDate;
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

    private final ValidationSupport validationSupport;
    private int lastRowIndex;

    protected AbstractEntitySheet() {
        validationSupport = new ValidationSupport();
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

    /**
     * Loads an entity into the panel, setting the editors' values to the ones of their respective properties.
     *
     * @param entity the entity to be loaded (not null)
     * @throws NullPointerException if {@code entity} is null
     */
    public abstract void load(E entity);

    /**
     * Clears the editors.
     */
    public abstract void clear();

    /**
     * Appends a row with a property editor and its label to the bottom of the panel. Subclasses must call this manually on each editor they
     * want to display. This method returns the editor that is appended so subclasses can create and append an editor in a single line, such
     * as:
     * <p>
     * <code>MyCustomEditor editor = appendEditor("Hello", MyCustomEditor::new);</code>
     * <p>
     * Label text is suffixed with a colon. If provided label text is null, some padding is added instead. Additionally, {@code ControlsFX}
     * {@link Validator}s can also be registered on the appended editor.
     *
     * @param labelText      the text to be displayed next to the editor (may be null)
     * @param editorSupplier an editor supplier (not null)
     * @param validators     editor validators
     * @param <C>            type of the editor
     * @return the appended editor
     * @throws NullPointerException if the supplied editor or the supplier itself is null
     */
    protected final <C extends Control> C appendEditor(String labelText, Supplier<C> editorSupplier, Validator<?>... validators) {
        Objects.requireNonNull(editorSupplier, "Editor supplier is null");
        C editor = Objects.requireNonNull(editorSupplier.get(), "Supplied editor is null");

        if (Objects.nonNull(validators)) {
            for (Validator<?> validator : validators) {
                validationSupport.registerValidator(editor, validator);
            }
        }
        validationSupport.initInitialDecoration();

        /*
         * HACK: I'm pretty sure column layout in this case should be done via ColumnConstraints, but seeing as how it doesn't work I don't
         * think I'm using the thing correctly, so this will do the trick instead.
         */
        editor.setMaxWidth(EDITOR_COLUMN_MAX_WIDTH);

        Node leftColumnNode = Objects.isNull(labelText) ? new Region() : new Label(labelText + ":");
        addRow(++lastRowIndex, leftColumnNode, editor);
        return editor;
    }

    /**
     * Convenience method to append a {@link CheckBox} with a label on its left side.
     *
     * @param labelText the text to be displayed next to the {@code CheckBox} (may be null)
     * @param initialValue  the box's initial check value
     * @param validators    editor validators
     * @return the appended {@code CheckBox}
     */
    @SafeVarargs
    protected final CheckBox appendCheckBox(String labelText, boolean initialValue, Validator<Boolean>... validators) {
        return appendEditor(labelText, () -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(initialValue);
            return checkBox;
        }, validators);
    }

    /**
     * Convenience method to append a {@link DatePicker} with a label.
     *
     * @param labelText    the text to be displayed next to the {@code DatePicker} (may be null)
     * @param initialValue the picker's initial value
     * @param validators   editor validators
     * @return the appended {@code DatePicker}
     */
    @SafeVarargs
    protected final DatePicker appendDatePicker(String labelText, LocalDate initialValue, Validator<LocalDate>... validators) {
        return appendEditor(labelText, () -> new DatePicker(initialValue), validators);
    }

    /**
     * Convenience method to append a {@link DatePicker} with a label.
     *
     * @param labelText    the text to be displayed next to the {@code DatePicker} (may be null)
     * @param validators   editor validators
     * @return the appended {@code DatePicker}
     */
    @SafeVarargs
    protected final DatePicker appendDatePicker(String labelText, Validator<LocalDate>... validators) {
        return appendEditor(labelText, DatePicker::new, validators);
    }
}
