// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

import java.util.Map;
import java.util.Objects;

public record Currency(String code, String name) {

    public Currency {
        Objects.requireNonNull(code, "Currency code must not be null");
        Objects.requireNonNull(name, "Currency name must not be null");
    }

    public static Currency ofMapEntry(Map.Entry<String, String> entry) {
        Objects.requireNonNull(entry, "Currency map entry must not be null");
        return new Currency(entry.getKey(), entry.getValue());
    }
}
