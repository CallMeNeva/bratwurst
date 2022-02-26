// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui;

import io.github.callmeneva.bratwurst.gui.action.BlockingDialogDisplayAction;
import io.github.callmeneva.bratwurst.gui.action.PlatformExitAction;
import io.github.callmeneva.bratwurst.gui.action.ResetAction;
import io.github.callmeneva.bratwurst.gui.browser.DataBrowserWithSheet;
import io.github.callmeneva.bratwurst.gui.browser.DataBrowserWithoutSheet;
import io.github.callmeneva.bratwurst.gui.control.DataBrowserTabPane;
import io.github.callmeneva.bratwurst.gui.dialog.AboutDialog;
import io.github.callmeneva.bratwurst.gui.dialog.AlertFactory;
import io.github.callmeneva.bratwurst.gui.dialog.HostInputDialog;
import io.github.callmeneva.bratwurst.gui.menu.MenuFactory;
import io.github.callmeneva.bratwurst.gui.menu.MenuItemFactory;
import io.github.callmeneva.bratwurst.gui.util.Icons;
import io.github.callmeneva.bratwurst.service.DataFetchFailureException;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Bratwurst extends Application {

    private static final String GLOBAL_WINDOW_TITLE = "Bratwurst";

    private final Alert dataFetchErrorAlert;
    private final Alert unknownErrorAlert;
    private final AboutDialog aboutDialog;

    public Bratwurst() {
        dataFetchErrorAlert = AlertFactory.create(Alert.AlertType.ERROR, "dialog.error.fetch.content");
        unknownErrorAlert = AlertFactory.create(Alert.AlertType.ERROR, "dialog.error.unknown.content");
        aboutDialog = new AboutDialog();
    }

    @Override
    public void init() {
        // Automatically set the application's global window title and icon to newly created windows for a more unified look
        Window.getWindows().addListener((ListChangeListener<? super Window>) change -> {
            while (change.next()) {
                change.getAddedSubList().forEach(window -> {
                    if (window instanceof Stage stage) {
                        stage.setTitle(GLOBAL_WINDOW_TITLE);
                        stage.getIcons().setAll(Icons.ALL_SIZES);
                    }
                });
            }
        });
    }

    @Override
    public void start(Stage stage) {
        //////////////
        // Tab pane //
        //////////////
        DataBrowserTabPane tabPane = new DataBrowserTabPane();
        tabPane.addBrowser(DataBrowserWithSheet.forLatestExchanges(), "browser.exchanges.latest");
        tabPane.addBrowser(DataBrowserWithSheet.forHistoricalExchanges(), "browser.exchanges.historical");
        tabPane.addBrowser(DataBrowserWithSheet.forTimeSeriesExchanges(), "browser.exchanges.series");
        tabPane.addBrowser(DataBrowserWithoutSheet.forCurrencies(), "browser.currencies");

        ////////////////
        // Menu items //
        ////////////////
        MenuItem exitMenuItem = MenuItemFactory.createRegular("menu.file.exit", new PlatformExitAction());

        MenuItem sendMenuItem = MenuItemFactory.createRegular("menu.request.send", event -> {
            try {
                tabPane.getSelectedBrowser().fetch();
            } catch (DataFetchFailureException e) {
                dataFetchErrorAlert.showAndWait();
            } catch (Exception e) {
                unknownErrorAlert.showAndWait();
            } finally {
                event.consume();
            }
        });

        MenuItem resetMenuItem = MenuItemFactory.createRegular("menu.request.reset", new ResetAction(tabPane));

        MenuItem setHostMenuItem = MenuItemFactory.createRegular("menu.request.host", new BlockingDialogDisplayAction<>(
                new HostInputDialog(),
                result -> tabPane.getBrowsers().forEach(browser -> browser.getFetcher().setHost(result))
        ));

        // FIXME: For some reason, the CheckMenuItem's initial value does not trigger the newly added selectedProperty ChangeListener. This means that
        //        setting the menu item's initial value to something that isn't the DataFetcher's current value will make them out of sync until the
        //        next external/programmatic check/uncheck. Since the fetchers' values are untouched since their initialization, as long as we set the
        //        CheckMenuItem to their default value (which is "true"), they should be de-facto in sync.
        MenuItem secureMenuItem = MenuItemFactory.createCheckable(
                "menu.request.https",
                value -> tabPane.getBrowsers().forEach(browser -> browser.getFetcher().setConnectionSecure(value)),
                true
        );

        MenuItem aboutMenuItem = MenuItemFactory.createRegular("menu.help.about", new BlockingDialogDisplayAction<>(aboutDialog));

        ////////////////////////////
        // Menu bar and its menus //
        ////////////////////////////
        Menu fileMenu = MenuFactory.create("menu.file", exitMenuItem);
        Menu requestMenu = MenuFactory.create("menu.request", sendMenuItem, resetMenuItem, setHostMenuItem, secureMenuItem);
        Menu helpMenu = MenuFactory.create("menu.help", aboutMenuItem);
        MenuBar menuBar = new MenuBar(fileMenu, requestMenu, helpMenu);

        ///////////////
        // Root pane //
        ///////////////
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        ////////////////
        // Main scene //
        ////////////////
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
