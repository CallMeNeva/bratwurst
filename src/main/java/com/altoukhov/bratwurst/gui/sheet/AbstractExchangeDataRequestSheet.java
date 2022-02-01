// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import com.altoukhov.bratwurst.gui.control.CurrencyComboBoxFactory;
import com.altoukhov.bratwurst.gui.converter.CurrencyStringConverter;
import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.service.request.AbstractExchangeDataRequest;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.CheckModel;

import java.util.List;

public abstract class AbstractExchangeDataRequestSheet<R extends AbstractExchangeDataRequest> extends AbstractEntitySheet<R> {

    // FIXME: Externalize UI strings
    private static final String AMOUNT_PICKER_LABEL = "Amount";
    private static final String BASE_PICKER_LABEL = "Base currency";
    private static final String TARGET_PICKER_LABEL = "Target currencies";

    private static final double DEFAULT_AMOUNT = 1.0;
    // FIXME: Externalize UI strings
    private static final CurrencyStringConverter PICKER_CURRENCY_STRING_CONVERTER = new CurrencyStringConverter("Default");

    private final SpinnerValueFactory<Double> amountPickerValueFactory;
    private final ComboBox<Currency> basePicker; // This one allows get/set operations directly, so we use the control itself
    private final CheckModel<Currency> targetCheckModel;

    protected AbstractExchangeDataRequestSheet() {
        Spinner<Double> amountPicker = appendEditor(AMOUNT_PICKER_LABEL, new Spinner<>(Double.MIN_VALUE, Double.MAX_VALUE, DEFAULT_AMOUNT, 0.1));
        amountPickerValueFactory = amountPicker.getValueFactory();

        basePicker = appendEditor(BASE_PICKER_LABEL, CurrencyComboBoxFactory.createWithSingleSelection());
        basePicker.setConverter(PICKER_CURRENCY_STRING_CONVERTER);

        CheckComboBox<Currency> targetPicker = appendEditor(TARGET_PICKER_LABEL, CurrencyComboBoxFactory.createWithMultiSelection());
        targetPicker.setConverter(PICKER_CURRENCY_STRING_CONVERTER);
        targetCheckModel = targetPicker.getCheckModel();
    }

    public final double getSelectedAmount() {
        return amountPickerValueFactory.getValue();
    }

    public final void selectAmount(double amount) {
        amountPickerValueFactory.setValue(amount);
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
        if (targets != null) {
            targets.forEach(targetCheckModel::check);
        }
    }

    @Override
    public void load(R entity) { // Non-final for inheritors
        selectAmount(entity.getAmount());
        selectBase(entity.getBase());
        selectTargets(entity.getTargets());
    }

    @Override
    public void clear() { // Non-final for inheritors
        selectAmount(DEFAULT_AMOUNT);
        selectBase(null);
        selectTargets(null);
    }
}
