// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.action;

import io.github.callmeneva.bratwurst.gui.util.Resettable;
import org.apache.commons.lang3.Validate;

public class ResetAction implements Action {

    private final Resettable resettable;

    public ResetAction(Resettable resettable) {
        this.resettable = Validate.notNull(resettable);
    }

    @Override
    public void run() {
        resettable.reset();
    }
}
