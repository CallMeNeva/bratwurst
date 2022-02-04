// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

import org.apache.commons.lang3.Validate;

import java.util.Map;

public record Currency(String code, String name) {

    public Currency {
        Validate.notNull(code);
        Validate.notNull(name);
    }

    public static Currency ofEntry(Map.Entry<String, String> entry) {
        Validate.notNull(entry);
        return new Currency(entry.getKey(), entry.getValue());
    }
}
