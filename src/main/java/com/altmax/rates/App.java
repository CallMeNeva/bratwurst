package com.altmax.rates;

import com.altmax.rates.view.PrimaryScene;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public final class App extends Application {

    private static final String NAME = "Rates";

    @Override
    public void start(Stage stage) throws IOException {
        PrimaryScene primaryScene = PrimaryScene.newPrimaryScene();
        stage.setTitle(NAME);
        stage.setScene(primaryScene);
        stage.setMinWidth(primaryScene.getMinWidth());
        stage.show();
    }

    public static String getName() {
        return NAME;
    }
}
