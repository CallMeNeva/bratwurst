// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.gui.control.AmountSlider;
import io.github.callmeneva.bratwurst.gui.control.MultiCurrencyCodeField;
import io.github.callmeneva.bratwurst.gui.control.SingleCurrencyCodeField;
import io.github.callmeneva.bratwurst.service.request.AbstractExchangeDataRequest;

import java.util.List;

public abstract class AbstractExchangeDataRequestSheet<R extends AbstractExchangeDataRequest> extends AbstractDataRequestSheet<R> {

    private final SingleCurrencyCodeField baseCodeInput;
    private final MultiCurrencyCodeField targetCodesInput;
    private final AmountSlider amountInput;

    protected AbstractExchangeDataRequestSheet() {
        baseCodeInput = appendEditor("sheet.request.input.base", new SingleCurrencyCodeField());
        targetCodesInput = appendEditor("sheet.request.input.targets", new MultiCurrencyCodeField());
        amountInput = appendEditor("sheet.request.input.amount", new AmountSlider());
    }

    protected final String getInputtedBaseCode() {
        return baseCodeInput.getText();
    }

    protected final List<String> getInputtedTargetCodes() {
        return targetCodesInput.getCodes();
    }

    protected final double getInputtedAmount() {
        return amountInput.getValue();
    }

    @Override
    public void reset() { // Non-final for inheritors
        super.reset();
        baseCodeInput.reset();
        targetCodesInput.reset();
        amountInput.reset();
    }
}
