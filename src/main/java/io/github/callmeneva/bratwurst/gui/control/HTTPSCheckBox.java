// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.util.Resettable;
import javafx.scene.control.CheckBox;
import org.apache.hc.core5.http.URIScheme;

public class HTTPSCheckBox extends CheckBox implements Resettable {

    public HTTPSCheckBox(URIScheme scheme) {
        setSchemeSelection(scheme);
    }

    public HTTPSCheckBox() {
        this(URIScheme.HTTPS);
    }

    public URIScheme getSchemeSelection() {
        return isSelected() ? URIScheme.HTTPS : URIScheme.HTTP;
    }

    public void setSchemeSelection(URIScheme scheme) {
        setSelected(scheme == URIScheme.HTTPS);
    }

    @Override
    public void reset() {
        setSchemeSelection(URIScheme.HTTPS);
    }
}
