// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.frankfurterdesktop.gui.sheet;

import com.altoukhov.frankfurterdesktop.gui.control.HostnameField;
import com.altoukhov.frankfurterdesktop.gui.control.PortNumberSpinner;
import com.altoukhov.frankfurterdesktop.service.FrankfurterService;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;

public final class HTTPHostSheet extends AbstractEntitySheet<HttpHost> {

    private static final HttpHost DEFAULT_HOST = FrankfurterService.PUBLIC_HOST;

    /*
     * TODO: Externalize UI strings
     */

    private static final String DEFAULT_HOST_TOGGLE_LABEL = "Default host";
    private static final String HOSTNAME_EDITOR_LABEL = "Hostname";
    private static final String PORT_EDITOR_LABEL = "Port";
    private static final String SECURE_CONNECTION_TOGGLE_LABEL = "Secure connection";

    private final CheckBox defaultHostToggle;
    private final HostnameField hostnameEditor;
    private final PortNumberSpinner portEditor;
    private final CheckBox secureConnectionToggle;

    public HTTPHostSheet() {
        defaultHostToggle = appendEditor(DEFAULT_HOST_TOGGLE_LABEL, CheckBox::new);
        hostnameEditor = appendEditor(HOSTNAME_EDITOR_LABEL, HostnameField::new);
        portEditor = appendEditor(PORT_EDITOR_LABEL, PortNumberSpinner::new);
        secureConnectionToggle = appendEditor(SECURE_CONNECTION_TOGGLE_LABEL, CheckBox::new);

        BooleanProperty customHostToggleSelectedProperty = defaultHostToggle.selectedProperty();
        customHostToggleSelectedProperty.bindBidirectional(hostnameEditor.disableProperty());
        customHostToggleSelectedProperty.bindBidirectional(portEditor.disableProperty());
        customHostToggleSelectedProperty.bindBidirectional(secureConnectionToggle.disableProperty());
    }

    @Override
    public HttpHost submit() throws InvalidSheetInputException {
        if (defaultHostToggle.isSelected()) {
            return DEFAULT_HOST;
        }

        String hostname = hostnameEditor.getText();

        /*
         * Not sure what exception does the HttpHost c-tor throw on empty/blank hostname (no info in JavaDocs currently), so just do a
         * manual check.
         */
        if (hostname.isEmpty() || hostname.isBlank()) {
            throw new InvalidSheetInputException();
        }

        int port = portEditor.getValue();
        URIScheme selectedScheme = secureConnectionToggle.isSelected() ? URIScheme.HTTPS : URIScheme.HTTP;

        return new HttpHost(selectedScheme.getId(), hostname, port);
    }

    @Override
    public void load(HttpHost entity) {
        if (entity.equals(DEFAULT_HOST)) {
            clear();
        } else {
            defaultHostToggle.setSelected(false);
            hostnameEditor.setText(entity.getHostName());
            portEditor.setValue(entity.getPort());
            secureConnectionToggle.setSelected(URIScheme.HTTPS.same(entity.getSchemeName()));
        }
    }

    @Override
    public void clear() {
        defaultHostToggle.setSelected(true);
        hostnameEditor.clear();
        portEditor.setDefaultValue();
        secureConnectionToggle.setSelected(true);
    }
}
