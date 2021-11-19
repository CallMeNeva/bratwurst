package io.github.altoukhovmax.frankfurterdesktop.gui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public final class MainView extends Scene {

    private static final String DEFAULT_RESOURCE_LOCATION = "/fxml/main_view.fxml";

    private MainView(@NotNull final Parent root) {
        super(root);
    }

    public static @NotNull MainView fromFXML(@NotNull final String path) throws IOException, NullPointerException {
        URL location = Objects.requireNonNull(MainView.class.getResource(path), "Failed to locate \"" + path + "\"");
        FXMLLoader loader = new FXMLLoader(location);
        return new MainView(loader.load());
    }

    public static @NotNull MainView fromFXML() throws IOException, NullPointerException {
        return fromFXML(DEFAULT_RESOURCE_LOCATION);
    }
}
