// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

@FunctionalInterface
public interface Action extends EventHandler<ActionEvent> {

    void run();

    @Override
    default void handle(ActionEvent event) {
        event.consume();
        run();
    }
}
