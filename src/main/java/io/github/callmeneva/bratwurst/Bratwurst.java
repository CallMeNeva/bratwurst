// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst;

import io.github.callmeneva.bratwurst.gui.BratwurstFX;
import javafx.application.Application;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class Bratwurst {

    public static void main(String[] args) {
        if (args.length > 0) {
            // FIXME: Externalize UI strings
            System.err.println("This program does not take any command line arguments!");
            return;
        }

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Application.launch(BratwurstFX.class, args);
    }
}
