// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst;

import com.altoukhov.bratwurst.gui.BratwurstGUI;
import javafx.application.Application;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class Bratwurst {

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        Application.launch(BratwurstGUI.class, args);
    }
}
