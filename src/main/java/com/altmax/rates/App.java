package com.altmax.rates;

import com.altmax.rates.view.MainScene;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.PrintStream;

public final class App extends Application {

    private final String NAME = "Rates";

    @Override
    public void start(Stage primaryStage) {
        /* Don't really wanna bother with the CLI in this project */
        boolean hasArgs = (getParameters().getRaw().size() > 0);
        if (hasArgs) {
            printAndExit(System.out, "Usage: java " + NAME.toLowerCase(), 0);
        }
        try {
            MainScene mainScene = MainScene.newMainScene();
            primaryStage.setTitle(NAME);
            primaryStage.setScene(mainScene);
            primaryStage.setMinWidth(mainScene.getMinWidth());
            primaryStage.show();
        } catch (Exception e) {
            printAndExit(System.err, "Failed to start application: " + e.getMessage(), -1);
        }
    }

    private static void printAndExit(PrintStream printer, String message, int exitStatus) {
        printer.println(message);
        System.exit(exitStatus);
    }
}
