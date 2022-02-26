// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.l10n;

import java.util.ResourceBundle;

public final class Localization {

    private static final String DIRECTORY_NAME = "l10n";
    private static final String BUNDLE_NAME = "l10n";
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(DIRECTORY_NAME + '.' + BUNDLE_NAME);

    private Localization() {}

    public static String get(String propertyName) {
        return BUNDLE.getString(propertyName);
    }
}
