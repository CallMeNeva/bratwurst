// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.gui.control.MultiCurrencyCodeTextField;
import io.github.callmeneva.bratwurst.gui.control.SingleCurrencyCodeTextField;
import io.github.callmeneva.bratwurst.service.request.AbstractExchangeDataRequest;
import javafx.scene.control.Slider;

import java.util.List;

public abstract class AbstractExchangeDataRequestSheet<R extends AbstractExchangeDataRequest> extends AbstractEntitySheet<R> {

    private static final double MIN_AMOUNT = 0.0;
    private static final double MAX_AMOUNT = 2000.0;
    private static final double MAJOR_TICK_UNIT = MAX_AMOUNT / 4.0;

    private final SingleCurrencyCodeTextField baseCodeTextField;
    private final MultiCurrencyCodeTextField targetCodesTextField;
    private final Slider amountSlider;

    protected AbstractExchangeDataRequestSheet() {
        baseCodeTextField = appendEditor("sheet.request.input.base", new SingleCurrencyCodeTextField());
        targetCodesTextField = appendEditor("sheet.request.input.targets", new MultiCurrencyCodeTextField());
        amountSlider = appendEditor("sheet.request.input.amount", new Slider(MIN_AMOUNT, MAX_AMOUNT, MIN_AMOUNT));

        amountSlider.setShowTickMarks(true);
        amountSlider.setShowTickLabels(true);
        amountSlider.setMajorTickUnit(MAJOR_TICK_UNIT);
    }

    protected final String getBaseCode() {
        return baseCodeTextField.getCode();
    }

    protected final List<String> getTargetCodes() {
        return targetCodesTextField.getCodes();
    }

    protected final double getAmount() {
        return amountSlider.getValue();
    }

    @Override
    public void reset() { // Non-final for inheritors
        baseCodeTextField.reset();
        targetCodesTextField.reset();
        amountSlider.setValue(MIN_AMOUNT);
    }
}
