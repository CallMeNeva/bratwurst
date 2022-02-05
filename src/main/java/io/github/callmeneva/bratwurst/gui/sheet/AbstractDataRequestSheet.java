// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.gui.control.HTTPSCheckBox;
import io.github.callmeneva.bratwurst.gui.control.HostnameField;
import io.github.callmeneva.bratwurst.gui.control.PortSpinner;
import io.github.callmeneva.bratwurst.service.request.AbstractDataRequest;
import org.apache.hc.core5.http.URIScheme;

public abstract class AbstractDataRequestSheet<R extends AbstractDataRequest> extends AbstractEntitySheet<R> {

    private static final String DEFAULT_HOSTNAME = "api.frankfurter.app";

    private final HTTPSCheckBox schemeInput;
    private final HostnameField hostnameInput;
    private final PortSpinner portInput;

    protected AbstractDataRequestSheet() {
        schemeInput = appendEditor("sheet.request.input.scheme", new HTTPSCheckBox());
        hostnameInput = appendEditor("sheet.request.input.hostname", new HostnameField(DEFAULT_HOSTNAME));
        portInput = appendEditor("sheet.request.input.port", new PortSpinner());
    }

    protected final URIScheme getInputtedScheme() {
        return schemeInput.getSchemeSelection();
    }

    protected final String getInputtedHostname() {
        return hostnameInput.getText();
    }

    protected final int getInputtedPort() {
        return portInput.getValue();
    }

    @Override
    public void reset() { // Non-final for inheritors
        schemeInput.reset();
        hostnameInput.reset();
        portInput.reset();
    }
}
