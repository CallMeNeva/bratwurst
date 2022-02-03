// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

import io.github.callmeneva.bratwurst.util.Arguments;

import java.util.Map;

public record Currency(String code, String name) {

    public Currency {
        Arguments.checkNull(code, "code");
        Arguments.checkNull(name, "name");
    }

    public static Currency of(Map.Entry<String, String> entry) {
        Arguments.checkNull(entry, "entry");
        return new Currency(entry.getKey(), entry.getValue());
    }
}