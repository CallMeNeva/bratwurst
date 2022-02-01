// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import com.altoukhov.bratwurst.gui.control.RepositoryMultiCurrencyPicker;
import com.altoukhov.bratwurst.gui.control.RepositorySingleCurrencyPicker;
import com.altoukhov.bratwurst.gui.converter.CurrencyStringConverter;
import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.model.CurrencyRepository;
import com.altoukhov.bratwurst.service.request.AbstractExchangeDataRequest;
import com.altoukhov.bratwurst.util.Arguments;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckModel;

import java.util.List;

public abstract class AbstractExchangeDataRequestSheet<R extends AbstractExchangeDataRequest> extends AbstractEntitySheet<R> {

    // FIXME: Externalize UI strings
    private static final String AMOUNT_PICKER_LABEL = "Amount";
    private static final String BASE_PICKER_LABEL = "Base currency";
    private static final String TARGET_PICKER_LABEL = "Target currencies";
    // ExchangeDataRequests are the only context in which we want to treat a null-pick as "default currency" instead of "no currency", so the sheet
    // provides a custom converter which, BTW, works with CheckComboBox only partially (default text is not rendered on no selection) for reasons that
    // are beyond me...
    private static final String DEFAULT_CURRENCY_TEXT = "Default";

    private static final double DEFAULT_AMOUNT = 1.0;
    private static final StringConverter<Currency> NULL_AS_DEFAULT_CURRENCY_STRING_CONVERTER = new CurrencyStringConverter() {
        @Override
        public String toString(Currency currency) {
            return (currency != null) ? currency.name() : DEFAULT_CURRENCY_TEXT;
        }
    };

    private final SpinnerValueFactory<Double> amountPickerValueFactory;
    private final RepositorySingleCurrencyPicker basePicker;
    private final RepositoryMultiCurrencyPicker targetPicker;
    private final CheckModel<Currency> targetPickerCheckModel;

    protected AbstractExchangeDataRequestSheet(CurrencyRepository currencyRepository) {
        Arguments.checkNull(currencyRepository, "currencyRepository"); // Not really required since currency pickers do this as well

        Spinner<Double> amountPicker = appendEditor(AMOUNT_PICKER_LABEL, new Spinner<>(Double.MIN_VALUE, Double.MAX_VALUE, DEFAULT_AMOUNT, 0.1));
        amountPickerValueFactory = amountPicker.getValueFactory();

        basePicker = appendEditor(BASE_PICKER_LABEL, new RepositorySingleCurrencyPicker(currencyRepository));
        basePicker.setConverter(NULL_AS_DEFAULT_CURRENCY_STRING_CONVERTER);

        targetPicker = appendEditor(TARGET_PICKER_LABEL, new RepositoryMultiCurrencyPicker(currencyRepository));
        targetPicker.setConverter(NULL_AS_DEFAULT_CURRENCY_STRING_CONVERTER);
        targetPickerCheckModel = targetPicker.getCheckModel();
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
        return targetPickerCheckModel.getCheckedItems();
    }

    public final void selectTargets(Iterable<Currency> targets) {
        targetPickerCheckModel.clearChecks();
        if (targets != null) {
            targets.forEach(targetPickerCheckModel::check);
        }
    }

    public final void reloadCurrencyPickers() {
        basePicker.reload();
        targetPicker.reload();
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
