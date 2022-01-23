// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.gui.control;

import com.altoukhov.frankfurterdesktop.gui.util.action.Action;
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

    private static final GlyphFont GLYPH_FONT = GlyphFontRegistry.font("FontAwesome");
    private static final Color DEFAULT_GLYPH_COLOR = Color.BLACK;
    private static final double DEFAULT_GLYPH_SIZE = 18;

    private Action action;
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

    public FontAwesomeButtonBuilder action(Action action) {
        this.action = action;
        return this;
    }

    public FontAwesomeButtonBuilder noAction() {
        return action(Action.EMPTY);
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
        button.setOnAction(event -> action.run());
        button.setTooltip(Objects.isNull(tooltipText) ? null : new Tooltip(tooltipText));

        return button;
    }
}
