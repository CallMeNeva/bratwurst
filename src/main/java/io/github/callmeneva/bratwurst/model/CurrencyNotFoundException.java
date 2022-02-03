// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
