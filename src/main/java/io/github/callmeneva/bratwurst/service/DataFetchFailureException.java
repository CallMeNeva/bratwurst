// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

public class DataFetchFailureException extends RuntimeException {

    public DataFetchFailureException(Throwable cause) {
        super(cause);
    }
}
