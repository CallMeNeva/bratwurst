// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.model;

import org.apache.commons.lang3.Validate;

import java.util.Map;

public record Sum(String currencyCode, double amount) implements Comparable<Sum> {

    public Sum {
        Validate.notNull(currencyCode);
    }

    public static Sum ofEntry(Map.Entry<String, Double> entry) {
        Validate.notNull(entry);
        return new Sum(entry.getKey(), entry.getValue());
    }

    @Override
    public int compareTo(Sum sum) {
        return Double.compare(amount, sum.amount);
    }
}
