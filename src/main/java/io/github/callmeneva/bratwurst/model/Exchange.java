// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

public record Exchange(Sum commitment, Sum result, LocalDate date) {

    public Exchange {
        Validate.notNull(commitment);
        Validate.notNull(result);
        Validate.notNull(date);
    }
}
