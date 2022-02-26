// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

import java.util.Map;
import java.util.Objects;

public record Sum(String currencyCode, double amount) implements Comparable<Sum> {

    public Sum {
        Objects.requireNonNull(currencyCode, "Sum currency code must not be null");
    }

    public static Sum ofMapEntry(Map.Entry<String, Double> entry) {
        Objects.requireNonNull(entry, "Sum map entry must not be null");
        return new Sum(entry.getKey(), entry.getValue());
    }

    @Override
    public int compareTo(Sum sum) {
        return Double.compare(amount, sum.amount);
    }
}
