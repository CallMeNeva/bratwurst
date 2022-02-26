// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

import java.time.LocalDate;
import java.util.Objects;

public record Exchange(Sum commitment, Sum result, LocalDate date) {

    public Exchange {
        Objects.requireNonNull(commitment, "Exchange's committed sum must not be null");
        Objects.requireNonNull(result, "Exchange's resulting sum must not be null");
        Objects.requireNonNull(date, "Exchange date must not be null");
    }
}
