// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop;

import com.altoukhov.frankfurterdesktop.gui.FrankfurterDesktopGUI;
import javafx.application.Application;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class FrankfurterDesktop {

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        Application.launch(FrankfurterDesktopGUI.class, args);
    }
}
