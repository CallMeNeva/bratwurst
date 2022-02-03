// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service;

public class ServiceException extends RuntimeException {

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
