// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.util.Resettable;
import javafx.scene.control.Slider;

public class AmountSlider extends Slider implements Resettable {

    private static final double MIN_BOUND = 0.0;
    private static final double MAX_BOUND = 1000.0;
    private static final double MAJOR_TICK_UNIT = (MAX_BOUND / 4.0);
    private static final double DEFAULT_VALUE = 1.0;

    public AmountSlider(double initialValue) {
        super(MIN_BOUND, MAX_BOUND, initialValue);
        setShowTickMarks(true);
        setShowTickLabels(true);
        setMajorTickUnit(MAJOR_TICK_UNIT);
        setSnapToTicks(true);
    }

    public AmountSlider() {
        this(DEFAULT_VALUE);
    }

    @Override
    public void reset() {
        setValue(DEFAULT_VALUE);
    }
}
