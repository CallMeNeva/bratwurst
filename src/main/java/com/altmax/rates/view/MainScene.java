package com.altmax.rates.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public final class MainScene extends Scene {

    private static final String FXML_RESOURCE_RELATIVE_PATH = "/fxml/main_scene.fxml";

    private MainScene(Parent root) {
        super(root);
    }

    public static MainScene newMainScene() throws NullPointerException, IOException {
        URL rootLocation = Objects.requireNonNull(
                MainScene.class.getResource(FXML_RESOURCE_RELATIVE_PATH),
                "Failed to find \"" + FXML_RESOURCE_RELATIVE_PATH + "\"");
        Parent root = FXMLLoader.load(rootLocation);
        return new MainScene(root);
    }

    public double getMinWidth() {
        return getRoot().prefWidth(-1);
    }
}
