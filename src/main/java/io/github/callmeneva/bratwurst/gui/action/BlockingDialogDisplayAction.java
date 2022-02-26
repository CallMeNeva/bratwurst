// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.action;

import javafx.scene.control.Dialog;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class BlockingDialogDisplayAction<R> implements Action {

    private final Dialog<R> dialog;
    private final Consumer<R> resultHandler;

    public BlockingDialogDisplayAction(Dialog<R> dialog, Consumer<R> resultHandler) {
        this.dialog = Objects.requireNonNull(dialog, "Dialog must not be null");
        this.resultHandler = Objects.requireNonNull(resultHandler, "Dialog result handler must not be null");
    }

    public BlockingDialogDisplayAction(Dialog<R> dialog) {
        this(dialog, result -> {});
    }

    @Override
    public void run() {
        Optional<R> result = dialog.showAndWait();
        result.ifPresent(resultHandler);
    }
}
