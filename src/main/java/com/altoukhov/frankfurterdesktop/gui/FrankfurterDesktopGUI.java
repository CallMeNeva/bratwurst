/*
 * Copyright 2021, 2022 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.altoukhov.frankfurterdesktop.gui;

import com.altoukhov.frankfurterdesktop.gui.notification.Notification;
import com.altoukhov.frankfurterdesktop.gui.scene.FrankfurterServiceSceneBuilder;
import com.altoukhov.frankfurterdesktop.gui.util.action.CurrencySyncAction;
import com.altoukhov.frankfurterdesktop.service.FrankfurterService;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public final class FrankfurterDesktopGUI extends Application {

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
