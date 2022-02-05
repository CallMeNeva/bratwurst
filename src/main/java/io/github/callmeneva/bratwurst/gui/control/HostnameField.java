// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.util.Resettable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class HostnameField extends FilteredTextField implements Resettable {

    private final String defaultHostname;

    public HostnameField(String defaultHostname) {
        super(HostnameField::isValid);

        Validate.isTrue(isValid(defaultHostname));
        this.defaultHostname = defaultHostname;
        setText(defaultHostname);
    }

    @Override
    public void reset() {
        setText(defaultHostname);
    }

    // FIXME: This is supposed to validate input as per whatever RFC that specifies the legal character set for a hostname. Current implementation is
    //        a dummy one purely for testing purposes and doesn't actually filter out anything other than whitespace, which will most likely lead to
    //        uncaught exceptions considering that components using this control assume the input to always be valid (theoretically speaking though,
    //        the only thing that matters is whether the hostname is valid *enough* for the construction of an HttpHost, since at the end of the day
    //        we use this hostname to create a request URI, and a URI creation exception is handled, while an HttpHost one is not).
    private static boolean isValid(String hostname) {
        return StringUtils.isNotBlank(hostname);
    }
}
