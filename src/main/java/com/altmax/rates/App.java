package com.altmax.rates;

import com.altmax.rates.view.PrimaryScene;

import javafx.application.Application;
import javafx.stage.Stage;

public final class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        PrimaryScene primaryScene = PrimaryScene.fromFXML();
        stage.setTitle("Rates");
        stage.setScene(primaryScene);
        stage.setMinWidth(primaryScene.getPreferredWidth());
        stage.show();
    }
}
