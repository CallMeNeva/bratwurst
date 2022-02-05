// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.converter;

import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.util.converter.IntegerStringConverter;
import org.apache.hc.core5.net.Ports;

import java.util.Objects;

public class PortStringConverter extends IntegerStringConverter {

    private final String defaultPortText;

    public PortStringConverter() {
        defaultPortText = Localization.getString("generic.default");
    }

    @Override
    public String toString(Integer value) {
        return Objects.equals(value, Ports.SCHEME_DEFAULT) ? defaultPortText : super.toString(value);
    }

    @Override
    public Integer fromString(String value) {
        return Objects.equals(value, defaultPortText) ? Ports.SCHEME_DEFAULT : super.fromString(value);
    }
}
