// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.sheet;

import com.altoukhov.bratwurst.gui.control.HostnameField;
import com.altoukhov.bratwurst.gui.control.PortNumberSpinner;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SpinnerValueFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.net.Ports;

public class HTTPHostSheet extends AbstractEntitySheet<HttpHost> {

    // FIXME: Externalize UI strings
    private static final String HOSTNAME_EDITOR_LABEL = "Hostname";
    private static final String PORT_EDITOR_LABEL = "Port";
    private static final String SECURE_CONNECTION_TOGGLE_LABEL = "Secure connection";

    private final HostnameField hostnameEditor;
    private final SpinnerValueFactory<Integer> portNumberSpinnerValueFactory;
    private final CheckBox secureConnectionToggle;

    public HTTPHostSheet() {
        hostnameEditor = appendEditor(HOSTNAME_EDITOR_LABEL, new HostnameField());

        PortNumberSpinner portEditor = appendEditor(PORT_EDITOR_LABEL, new PortNumberSpinner());
        portNumberSpinnerValueFactory = portEditor.getValueFactory();

        secureConnectionToggle = appendEditor(SECURE_CONNECTION_TOGGLE_LABEL, new CheckBox());
    }

    public String getEnteredHostname() {
        return hostnameEditor.getText();
    }

    public void enterHostname(String hostname) {
        hostnameEditor.setText(hostname);
    }

    public int getSelectedPort() {
        return portNumberSpinnerValueFactory.getValue();
    }

    public void selectPort(int port) {
        portNumberSpinnerValueFactory.setValue(port);
    }

    public boolean isSecureConnectionSelected() {
        return secureConnectionToggle.isSelected();
    }

    public void setSecureConnectionSelected(boolean selected) {
        secureConnectionToggle.setSelected(selected);
    }

    @Override
    public HttpHost submit() throws InvalidSheetInputException {
        String hostname = getEnteredHostname();

        // Not sure how exactly hostname is validated in HttpHost, so just manually check for empty/blank and hope that's enough
        if (hostname.isEmpty() || hostname.isBlank()) {
            throw new InvalidSheetInputException();
        }

        int port = getSelectedPort();
        URIScheme selectedScheme = isSecureConnectionSelected() ? URIScheme.HTTPS : URIScheme.HTTP;

        return new HttpHost(selectedScheme.getId(), hostname, port);
    }

    @Override
    public void load(HttpHost entity) {
        enterHostname(entity.getHostName());
        selectPort(entity.getPort());
        setSecureConnectionSelected(URIScheme.HTTPS.same(entity.getSchemeName()));
    }

    @Override
    public void clear() {
        hostnameEditor.clear(); // Or set null?
        selectPort(Ports.SCHEME_DEFAULT);
        setSecureConnectionSelected(true);
    }
}
