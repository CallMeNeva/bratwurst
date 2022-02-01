// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.util;

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
}
