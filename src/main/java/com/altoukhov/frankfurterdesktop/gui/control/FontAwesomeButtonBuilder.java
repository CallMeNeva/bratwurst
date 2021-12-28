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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.util.Objects;

public final class FontAwesomeButtonBuilder implements Builder<Button> {

    private static final EventHandler<ActionEvent> EMPTY_ACTION_EVENT_HANDLER = actionEvent -> {};
    private static final GlyphFont GLYPH_FONT = GlyphFontRegistry.font("FontAwesome");
    private static final Color DEFAULT_GLYPH_COLOR = Color.BLACK;
    private static final double DEFAULT_GLYPH_SIZE = 16;

    private EventHandler<ActionEvent> actionEventHandler;
    private FontAwesome.Glyph glyph;
    private Color glyphColor;
    private double glyphSize;
    private String tooltipText;

    public FontAwesomeButtonBuilder(FontAwesome.Glyph glyph) {
        noAction();
        glyph(glyph);
        defaultGlyphColor();
        defaultGlyphSize();
        noTooltip();
    }

    public FontAwesomeButtonBuilder onAction(EventHandler<ActionEvent> handler) {
        actionEventHandler = handler;
        return this;
    }

    public FontAwesomeButtonBuilder noAction() {
        return onAction(EMPTY_ACTION_EVENT_HANDLER);
    }

    public FontAwesomeButtonBuilder glyph(FontAwesome.Glyph glyph) {
        this.glyph = glyph;
        return this;
    }

    public FontAwesomeButtonBuilder glyphColor(Color color) {
        glyphColor = color;
        return this;
    }

    public FontAwesomeButtonBuilder defaultGlyphColor() {
        return glyphColor(DEFAULT_GLYPH_COLOR);
    }

    public FontAwesomeButtonBuilder glyphSize(double size) {
        glyphSize = size;
        return this;
    }

    public FontAwesomeButtonBuilder defaultGlyphSize() {
        return glyphSize(DEFAULT_GLYPH_SIZE);
    }

    public FontAwesomeButtonBuilder tooltipText(String text) {
        tooltipText = text;
        return this;
    }

    public FontAwesomeButtonBuilder noTooltip() {
        return tooltipText(null);
    }

    @Override
    public Button build() {
        Glyph buttonGraphic = GLYPH_FONT.create(glyph)
                .color(glyphColor)
                .size(glyphSize);

        Button button = new Button("", buttonGraphic);
        button.setOnAction(actionEventHandler);
        button.setTooltip(Objects.isNull(tooltipText) ? null : new Tooltip(tooltipText));

        return button;
    }
}
