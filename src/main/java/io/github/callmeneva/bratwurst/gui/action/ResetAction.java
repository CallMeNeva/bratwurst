// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.action;

import io.github.callmeneva.bratwurst.gui.util.Resettable;

import java.util.Objects;

public class ResetAction implements Action {

    private final Resettable resettable;

    public ResetAction(Resettable resettable) {
        this.resettable = Objects.requireNonNull(resettable, "Resettable must not be null");
    }

    @Override
    public void run() {
        resettable.reset();
    }
}
