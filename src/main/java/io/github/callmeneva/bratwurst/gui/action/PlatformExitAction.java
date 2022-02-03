// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.action;

import javafx.application.Platform;

public class PlatformExitAction implements Action {

    @Override
    public void run() {
        Platform.exit();
    }
}
