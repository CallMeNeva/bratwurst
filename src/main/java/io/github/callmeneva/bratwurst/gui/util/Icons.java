// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.util;

import javafx.scene.image.Image;

import java.nio.file.Path;
import java.util.List;

public final class Icons {

    public static final Image PX_16 = load(16);
    public static final Image PX_24 = load(24);
    public static final Image PX_32 = load(32);
    public static final Image PX_64 = load(64);
    public static final Image PX_128 = load(128);
    public static final Image PX_256 = load(256);
    public static final Image PX_512 = load(512);
    public static final List<Image> ALL_SIZES = List.of(PX_16, PX_24, PX_32, PX_64, PX_128, PX_256, PX_512);

    private static final String DIRECTORY_NAME = "icons";
    private static final String DEFAULT_EXTENSION = "png";

    private Icons() {}

    private static Image load(int size) {
        String filename = (Integer.toString(size) + '.' + DEFAULT_EXTENSION);
        Path path = Path.of(DIRECTORY_NAME, filename);
        return new Image(path.toString());
    }
}
