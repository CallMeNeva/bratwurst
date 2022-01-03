/*
 * Copyright 2021, 2022 Maxim Altoukhov
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

import com.altoukhov.frankfurterdesktop.gui.util.converter.CurrencyStringConverter;
import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.model.CurrencyRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.util.Objects;
import java.util.Set;

public final class CurrencyComboBoxFactory {

    private static final ObservableList<Currency> SHARED_ITEMS = FXCollections.observableArrayList();
    private static final StringConverter<Currency> CONVERTER = new CurrencyStringConverter();

    private CurrencyComboBoxFactory() {}

    public static ComboBox<Currency> createWithSingleSelection() {
        ComboBox<Currency> comboBox = new ComboBox<>(SHARED_ITEMS);
        comboBox.setConverter(CONVERTER);
        return comboBox;
    }

    public static CheckComboBox<Currency> createWithMultiSelection() {
        CheckComboBox<Currency> comboBox = new CheckComboBox<>(SHARED_ITEMS);
        comboBox.setConverter(CONVERTER);
        return comboBox;
    }

    /*
     * According to the JavaDoc of FXCollections, mutations on the underlying container are not reported. The registry's underlying set is
     * also not observable. This means that the container of currencies shared between pickers needs to be reloaded manually after the
     * registry's contents have been updated.
     */
    public static void reloadSharedItems(CurrencyRegistry registry) {
        Objects.requireNonNull(registry, "Provided currency registry is null");
        Set<Currency> items = registry.getCurrencies();
        SHARED_ITEMS.setAll(items);
    }

    public static void reloadSharedItems() {
        reloadSharedItems(CurrencyRegistry.GLOBAL);
    }
}
