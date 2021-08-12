package io.github.altoukhovmax.rates.gui;

import io.github.altoukhovmax.rates.gui.view.MainView;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class GraphicalApplication extends Application {

    private static final String WINDOW_TITLE = "Rates";
    private static final Image WINDOW_ICON = new Image("/icons/app.png");
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphicalApplication.class);

    @Override
    public void init() {
        Window.getWindows().addListener((ListChangeListener<Window>) change -> {
            while (change.next()) {
                change.getAddedSubList().forEach(window -> {
                    if (window instanceof final Stage stage) {
                        stage.getIcons().setAll(WINDOW_ICON);
                        stage.setTitle(WINDOW_TITLE);
                    }
                });
            }
        });
        LOGGER.info("Initialized application");
    }

    @Override
    public void start(final Stage stage) throws IOException, NullPointerException {
        stage.setScene(MainView.fromFXML());
        LOGGER.info("Started application");
        stage.show();
    }

    @Override
    public void stop() {
        LOGGER.info("Terminated application");
    }
}
