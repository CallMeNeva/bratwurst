// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.converter.CurrencyStringConverter;
import io.github.callmeneva.bratwurst.model.Currency;
import io.github.callmeneva.bratwurst.model.CurrencyRepository;
import io.github.callmeneva.bratwurst.util.Arguments;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

// NOTE: Since ComboBox and CheckComboBox share some of their API without actually sharing a common base, this basically leads to choosing between
// minor code duplication versus a complete inheritance-related design mess
public class RepositorySingleCurrencyPicker extends ComboBox<Currency> {

    private static final StringConverter<Currency> DEFAULT_STRING_CONVERTER = new CurrencyStringConverter();

    private final CurrencyRepository repository;

    public RepositorySingleCurrencyPicker(CurrencyRepository repository) {
        this.repository = Arguments.checkNull(repository, "repository");
        setConverter(DEFAULT_STRING_CONVERTER);
        reload();
    }

    public void reload() {
        ObservableList<Currency> pickerContents = getItems();
        pickerContents.setAll(repository.getCurrencies());
    }
}
