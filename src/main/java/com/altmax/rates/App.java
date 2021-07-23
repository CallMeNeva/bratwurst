package com.altmax.rates;

import com.altmax.rates.view.PrimaryScene;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        PrimaryScene primaryScene = PrimaryScene.fromFXML();
        stage.setTitle("Rates");
        stage.setScene(primaryScene);
        stage.getIcons().add(new Image("/icons/currency_exchange.png"));
        stage.show();
    }
}
