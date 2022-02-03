// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.gui.control.RepositoryMultiCurrencyPicker;
import io.github.callmeneva.bratwurst.gui.control.RepositorySingleCurrencyPicker;
import io.github.callmeneva.bratwurst.gui.converter.CurrencyStringConverter;
import io.github.callmeneva.bratwurst.l10n.Localization;
import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.model.CurrencyRepository;
import io.github.callmeneva.bratwurst.service.request.AbstractExchangeDataRequest;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckModel;

import java.util.List;

public abstract class AbstractExchangeDataRequestSheet<R extends AbstractExchangeDataRequest> extends AbstractEntitySheet<R> {

    private static final String AMOUNT_PICKER_LABEL = Localization.getString("request-sheet.amount-input-label");
    private static final String BASE_PICKER_LABEL = Localization.getString("request-sheet.base-input-label");
    private static final String TARGET_PICKER_LABEL = Localization.getString("request-sheet.target-input-label");

    private static final double DEFAULT_AMOUNT = 1.0;
    // Exchange data requests are the only context in which we want to treat a null-pick as "default currency" instead of "no currency", so the sheet
    // provides a custom converter which, BTW, works with CheckComboBox only partially (default text is simply not rendered on no selection) for
    // reasons that are beyond me...
    private static final StringConverter<Currency> REQUEST_SHEET_CURRENCY_CONVERTER = new CurrencyStringConverter() {

        private static final String NO_SELECTION_TEXT = Localization.getString("generic.default-value-text");

        @Override
        public String toString(Currency currency) {
            return (currency != null) ? currency.name() : NO_SELECTION_TEXT;
        }
    };

    private final SpinnerValueFactory<Double> amountPickerValueFactory;
    private final RepositorySingleCurrencyPicker basePicker;
    private final RepositoryMultiCurrencyPicker targetPicker;
    private final CheckModel<Currency> targetPickerCheckModel;

    protected AbstractExchangeDataRequestSheet(CurrencyRepository currencyRepository) {
        Spinner<Double> amountPicker = appendEditor(AMOUNT_PICKER_LABEL, new Spinner<>(Double.MIN_VALUE, Double.MAX_VALUE, DEFAULT_AMOUNT, 0.1));
        amountPickerValueFactory = amountPicker.getValueFactory();

        basePicker = appendEditor(BASE_PICKER_LABEL, new RepositorySingleCurrencyPicker(currencyRepository));
        basePicker.setConverter(REQUEST_SHEET_CURRENCY_CONVERTER);

        targetPicker = appendEditor(TARGET_PICKER_LABEL, new RepositoryMultiCurrencyPicker(currencyRepository));
        targetPicker.setConverter(REQUEST_SHEET_CURRENCY_CONVERTER);
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
