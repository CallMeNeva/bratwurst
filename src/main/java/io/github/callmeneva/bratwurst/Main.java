// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst;

import io.github.callmeneva.bratwurst.gui.Bratwurst;
import javafx.application.Application;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class Main {

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Application.launch(Bratwurst.class, args);
    }
}
