// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui;

import com.altoukhov.bratwurst.gui.notification.Notification;
import com.altoukhov.bratwurst.gui.scene.FrankfurterServiceSceneBuilder;
import com.altoukhov.bratwurst.gui.util.action.CurrencySyncAction;
import com.altoukhov.bratwurst.service.FrankfurterService;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public final class BratwurstGUI extends Application {

    private static final String WINDOW_TITLE = "Frankfurter Desktop";
    private static final Image WINDOW_ICON = new Image("/icons/exchange-rate.png");

    @Override
    public void init() {
        /* Set up a listener to automatically set the application's window title and icon to newly created child windows, such as dialogs */
        Window.getWindows().addListener((ListChangeListener<Window>) change -> {
            while (change.next()) {
                change.getAddedSubList().forEach(window -> {
                    if (window instanceof Stage stage) {
                        stage.setTitle(WINDOW_TITLE);
                        stage.getIcons().setAll(WINDOW_ICON);
                    }
                });
            }
        });
    }

    @Override
    public void start(Stage stage) {
        FrankfurterService service = new FrankfurterService();

        CurrencySyncAction initialCurrencySyncAction = new CurrencySyncAction(service, Notification.failedInitialCurrencySync());
        initialCurrencySyncAction.run();

        Scene scene = new FrankfurterServiceSceneBuilder(service)
                .aboutNotification(Notification.about())
                .currencySyncNotification(Notification.successfulCurrencySync())
                .hostChangeNotification(Notification.hostChange())
                .serviceExceptionHandler(Notification.serviceError())
                .badInputExceptionHandler(Notification.badSheetInput())
                .build();

        stage.setScene(scene);
        stage.show();
    }
}
