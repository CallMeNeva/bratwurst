// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.util.function;

@FunctionalInterface
public interface ExceptionHandler<E extends Exception> {

    void handle(E exception);

    static <E extends Exception> ExceptionHandler<E> ignoring() {
        return e -> {};
    }
}
