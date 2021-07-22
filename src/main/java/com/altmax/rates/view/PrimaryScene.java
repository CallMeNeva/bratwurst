package com.altmax.rates.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public final class PrimaryScene extends Scene {

    private static final String RELATIVE_PATH_TO_FXML = "/fxml/primary_scene.fxml";

    private PrimaryScene(@NotNull Parent root) {
        super(root);
    }

    @NotNull
    public static PrimaryScene fromFXML() throws IOException {
        URL rootLocation = Objects.requireNonNull(
                PrimaryScene.class.getResource(RELATIVE_PATH_TO_FXML),
                "Failed to find \"" + RELATIVE_PATH_TO_FXML + "\"");
        Parent root = FXMLLoader.load(rootLocation);
        return new PrimaryScene(root);
    }

    public double getPreferredWidth() {
        return getRoot().prefWidth(-1);
    }
}
