// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.action;

@FunctionalInterface
public interface Action {

    Action EMPTY = () -> {};

    void run();
}
