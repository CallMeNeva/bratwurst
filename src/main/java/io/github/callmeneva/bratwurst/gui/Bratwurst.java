// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui;

import io.github.callmeneva.bratwurst.gui.action.DialogDisplayAction;
import io.github.callmeneva.bratwurst.gui.action.PlatformExitAction;
import io.github.callmeneva.bratwurst.gui.action.ResetAction;
import io.github.callmeneva.bratwurst.gui.browser.CurrencyDataBrowser;
import io.github.callmeneva.bratwurst.gui.browser.HistoricalExchangeDataBrowser;
import io.github.callmeneva.bratwurst.gui.browser.LatestExchangeDataBrowser;
import io.github.callmeneva.bratwurst.gui.browser.TimeSeriesExchangeDataBrowser;
import io.github.callmeneva.bratwurst.gui.control.DataBrowserTabPane;
import io.github.callmeneva.bratwurst.gui.dialog.AlertFactory;
import io.github.callmeneva.bratwurst.gui.menu.MenuFactory;
import io.github.callmeneva.bratwurst.gui.menu.MenuItemFactory;
import io.github.callmeneva.bratwurst.gui.util.Icons;
import io.github.callmeneva.bratwurst.service.DataFetchFailureException;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;

public class Bratwurst extends Application {

    private static final String GLOBAL_WINDOW_TITLE = "Bratwurst";

    private final Alert dataFetchErrorAlert;
    private final Alert unknownErrorAlert;

    public Bratwurst() {
        dataFetchErrorAlert = AlertFactory.create(Alert.AlertType.ERROR, "dialog.error.fetch");
        unknownErrorAlert = AlertFactory.create(Alert.AlertType.ERROR, "dialog.error.unknown");
    }

    @Override
    public void init() {
        // Automatically set the application's global window title and icon to newly created windows for a more unified look
        ObservableList<Window> windows = Window.getWindows();
        windows.addListener((ListChangeListener<? super Window>) change -> {
            while (change.next()) {
                List<? extends Window> addedSubList = change.getAddedSubList();
                addedSubList.forEach(window -> {
                    if (window instanceof Stage stage) {
                        ObservableList<Image> stageIcons = stage.getIcons();
                        stage.setTitle(GLOBAL_WINDOW_TITLE);
                        stageIcons.setAll(Icons.ALL_SIZES);
                    }
                });
            }
        });
    }

    @Override
    public void start(Stage stage) {
        DataBrowserTabPane tabPane = new DataBrowserTabPane();
        tabPane.addBrowser(new LatestExchangeDataBrowser(), "browser.exchanges.latest");
        tabPane.addBrowser(new HistoricalExchangeDataBrowser(), "browser.exchanges.historical");
        tabPane.addBrowser(new TimeSeriesExchangeDataBrowser(), "browser.exchanges.series");
        tabPane.addBrowser(new CurrencyDataBrowser(), "browser.currencies");

        MenuItem exitMenuItem = MenuItemFactory.create("menu.file.exit", new PlatformExitAction());
        Menu fileMenu = MenuFactory.create("menu.file", exitMenuItem);

        MenuItem sendMenuItem = MenuItemFactory.create("menu.request.send", event -> {
            // FIXME: Move this somewhere else
            try {
                tabPane.submitSelected();
            } catch (DataFetchFailureException e) {
                dataFetchErrorAlert.showAndWait();
            } catch (Exception e) {
                unknownErrorAlert.showAndWait();
            } finally {
                event.consume();
            }
        });
        MenuItem resetMenuItem = MenuItemFactory.create("menu.request.reset", new ResetAction(tabPane));
        Menu requestMenu = MenuFactory.create("menu.request", sendMenuItem, resetMenuItem);

        MenuItem aboutMenuItem = MenuItemFactory.create("menu.help.about", DialogDisplayAction.withAboutDialog());
        Menu helpMenu = MenuFactory.create("menu.help", aboutMenuItem);

        MenuBar menuBar = new MenuBar(fileMenu, requestMenu, helpMenu);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
