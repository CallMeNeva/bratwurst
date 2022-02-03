// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.util;

import java.util.function.Function;

public final class Arguments {

    private Arguments() {}

    // This exists and is used instead of Objects::requireNonNull only to provide a generic exception message for null-checks. Instead of throwing
    // an NPE by delegating to Objects::requireNonNull, an IllegalArgumentException is manually thrown simply because it's (supposedly) a better
    // semantic fit. Either way, it's not a big deal.
    public static <T> T checkNull(T argument, String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException("Provided argument \"" + argumentName + "\" is null");
        }
        return argument;
    }

    public static <T, R> R nullOrElse(T argument, R resultIfNotNull) {
        return (argument == null) ? null : resultIfNotNull;
    }

    public static <T, R> R nullOrElseApply(T argument, Function<T, R> function) {
        Arguments.checkNull(argument, "function");
        return nullOrElse(argument, function.apply(argument));
    }
}
