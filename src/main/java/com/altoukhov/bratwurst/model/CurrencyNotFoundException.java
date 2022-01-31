// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.model;

import java.util.NoSuchElementException;

public class CurrencyNotFoundException extends NoSuchElementException {

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
