// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.util.action;

@FunctionalInterface
public interface Action {

    Action EMPTY = () -> {};

    void run();
}
