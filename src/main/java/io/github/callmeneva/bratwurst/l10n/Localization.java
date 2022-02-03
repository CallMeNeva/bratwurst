// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.l10n;

import java.util.ResourceBundle;

public final class Localization {

    private static final String PATH_ROOT = "l10n";
    private static final String BUNDLE_NAME = "l10n";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(PATH_ROOT + '.' + BUNDLE_NAME);

    private Localization() {}

    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
