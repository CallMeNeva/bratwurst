// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.sheet;

import io.github.callmeneva.bratwurst.gui.control.HostnameField;
import io.github.callmeneva.bratwurst.gui.control.PortNumberSpinner;
import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SpinnerValueFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.net.Ports;

public class HTTPHostSheet extends AbstractEntitySheet<HttpHost> {

    private static final String HOSTNAME_EDITOR_LABEL = Localization.getString("host-sheet.hostname-input-label");
    private static final String PORT_EDITOR_LABEL = Localization.getString("host-sheet.port-input-label");
    private static final String SECURE_CONNECTION_TOGGLE_LABEL = Localization.getString("host-sheet.connection-security-input-label");

    private final HostnameField hostnameEditor;
    private final SpinnerValueFactory<Integer> portNumberSpinnerValueFactory;
    private final CheckBox secureConnectionToggle;

    public HTTPHostSheet() {
        hostnameEditor = appendEditor(HOSTNAME_EDITOR_LABEL, new HostnameField());

        PortNumberSpinner portEditor = appendEditor(PORT_EDITOR_LABEL, new PortNumberSpinner());
        portNumberSpinnerValueFactory = portEditor.getValueFactory();

        secureConnectionToggle = appendEditor(SECURE_CONNECTION_TOGGLE_LABEL, new CheckBox());
        selectSecureConnection(true);
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

    public void selectSecureConnection(boolean selected) {
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
        selectSecureConnection(URIScheme.HTTPS.same(entity.getSchemeName()));
    }

    @Override
    public void clear() {
        hostnameEditor.clear(); // Or set null?
        selectPort(Ports.SCHEME_DEFAULT);
        selectSecureConnection(true);
    }
}
