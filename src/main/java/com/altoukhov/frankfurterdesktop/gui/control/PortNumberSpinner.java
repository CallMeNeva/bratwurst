/*
 * Copyright 2022 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.altoukhov.frankfurterdesktop.gui.control;

import com.altoukhov.frankfurterdesktop.gui.util.converter.PortNumberStringConverter;
import javafx.scene.control.Spinner;
import org.apache.hc.core5.net.Ports;

public final class PortNumberSpinner extends Spinner<Integer> {

    public PortNumberSpinner() {
        super(Ports.SCHEME_DEFAULT, Ports.MAX_VALUE, Ports.MIN_VALUE);
        getValueFactory().setConverter(new PortNumberStringConverter());
    }

    public PortNumberSpinner(int port) {
        this();
        setValue(port);
    }

    public void setValue(int port) {
        getValueFactory().setValue(port);
    }

    public void setDefaultValue() {
        getValueFactory().setValue(Ports.SCHEME_DEFAULT);
    }
}
