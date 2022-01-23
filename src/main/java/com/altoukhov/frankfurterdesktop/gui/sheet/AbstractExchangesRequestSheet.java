// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.gui.sheet;

import com.altoukhov.frankfurterdesktop.gui.control.CurrencyComboBoxFactory;
import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.service.request.AbstractExchangeDataRequest;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import org.controlsfx.control.CheckModel;

import java.util.List;
import java.util.Objects;

public abstract class AbstractExchangesRequestSheet<R extends AbstractExchangeDataRequest> extends AbstractEntitySheet<R> {

    /*
     * TODO: Externalize UI strings
     */

    private static final String AMOUNT_PICKER_LABEL = "Amount";
    private static final String BASE_PICKER_LABEL = "Base currency";
    private static final String TARGET_PICKER_LABEL = "Target currencies";

    private static final double DEFAULT_AMOUNT = 1.0;

    private final Spinner<Double> amountPicker;
    private final ComboBox<Currency> basePicker;
    private final CheckModel<Currency> targetCheckModel; /* All of our operations deal with the CheckModel instead of the actual control */

    protected AbstractExchangesRequestSheet() {
        amountPicker = appendEditor(AMOUNT_PICKER_LABEL, () -> new Spinner<>(Double.MIN_VALUE, Double.MAX_VALUE, DEFAULT_AMOUNT, 0.1));
        basePicker = appendEditor(BASE_PICKER_LABEL, CurrencyComboBoxFactory::createWithSingleSelection);
        targetCheckModel = appendEditor(TARGET_PICKER_LABEL, CurrencyComboBoxFactory::createWithMultiSelection).getCheckModel();
    }

    public final double getSelectedAmount() {
        return amountPicker.getValue();
    }

    public final void selectAmount(double amount) {
        amountPicker.getValueFactory().setValue(amount);
    }

    public final Currency getSelectedBase() {
        return basePicker.getValue();
    }

    public final void selectBase(Currency base) {
        basePicker.setValue(base);
    }

    public final List<Currency> getSelectedTargets() {
        return targetCheckModel.getCheckedItems();
    }

    public final void selectTargets(Iterable<Currency> targets) {
        targetCheckModel.clearChecks();
        if (Objects.nonNull(targets)) {
            targets.forEach(targetCheckModel::check);
        }
    }

    @Override
    public void load(R entity) { /* Non-final: meant to be delegated to and extended by subclass if additional properties are present */
        selectAmount(entity.getAmount());
        selectBase(entity.getBase());
        selectTargets(entity.getTargets());
    }

    @Override
    public void clear() { /* Non-final: meant to be delegated to and extended by subclass if additional properties are present */
        selectAmount(DEFAULT_AMOUNT);
        selectBase(null);
        selectTargets(null);
    }
}
