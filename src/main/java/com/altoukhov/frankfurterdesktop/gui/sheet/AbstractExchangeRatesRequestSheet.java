/*
 * Copyright 2021 Maxim Altoukhov
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

package com.altoukhov.frankfurterdesktop.gui.sheet;

import com.altoukhov.frankfurterdesktop.gui.control.CurrencyComboBoxFactory;
import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.service.request.AbstractExchangeRatesRequest;
import javafx.scene.control.ComboBox;
import org.controlsfx.control.CheckModel;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.Validator;

import java.util.List;
import java.util.Objects;

public abstract class AbstractExchangeRatesRequestSheet<R extends AbstractExchangeRatesRequest> extends AbstractEntitySheet<R> {

    private static final String BASE_PICKER_LABEL = "Base currency";
    private static final String NO_BASE_SELECTION_MESSAGE = "The Euro will be used by default if no specific selection is made";
    private static final String TARGET_PICKER_LABEL = "Target currencies";

    private final ComboBox<Currency> basePicker;
    private final CheckModel<Currency> targetCheckModel; /* All of our operations deal with the CheckModel instead of the actual control */

    protected AbstractExchangeRatesRequestSheet() {
        basePicker = appendEditor(
                BASE_PICKER_LABEL,
                CurrencyComboBoxFactory::createWithSingleSelection,
                Validator.createEmptyValidator(NO_BASE_SELECTION_MESSAGE, Severity.WARNING)
        );

        targetCheckModel = appendEditor(
                TARGET_PICKER_LABEL,
                CurrencyComboBoxFactory::createWithMultiSelection
                /* TODO: Validator that warns about no specific targets == all targets */
        ).getCheckModel();
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
        Objects.requireNonNull(entity, "Provided request is null");
        selectBase(entity.getBase());
        selectTargets(entity.getTargets());
    }

    @Override
    public void clear() { /* Non-final: meant to be delegated to and extended by subclass if additional properties are present */
        selectBase(null);
        selectTargets(null);
    }
}
