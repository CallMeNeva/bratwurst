// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

import io.github.callmeneva.bratwurst.util.Arguments;

import java.time.LocalDate;

public record Exchange(Sum commitment, Sum result, LocalDate date) {

    public Exchange {
        Arguments.checkNull(commitment, "commitment");
        Arguments.checkNull(result, "result");
        Arguments.checkNull(date, "date");
    }
}
