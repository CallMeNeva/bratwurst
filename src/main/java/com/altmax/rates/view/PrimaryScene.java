package com.altmax.rates.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public final class PrimaryScene extends Scene {

    private static final String FXML_RESOURCE_RELATIVE_PATH = "/fxml/primary_scene.fxml";

    private PrimaryScene(@NotNull Parent root) {
        super(root);
    }

    public static PrimaryScene newPrimaryScene() throws IOException {
        URL rootLocation = Objects.requireNonNull(
                PrimaryScene.class.getResource(FXML_RESOURCE_RELATIVE_PATH),
                "Failed to find \"" + FXML_RESOURCE_RELATIVE_PATH + "\"");
        Parent root = FXMLLoader.load(rootLocation);
        return new PrimaryScene(root);
    }

    public double getMinWidth() {
        return getRoot().prefWidth(-1);
    }
}
