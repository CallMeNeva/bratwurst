/*
 * Copyright 2021 Maxim Altoukhov
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

import com.altoukhov.frankfurterdesktop.gui.scene.FrankfurterServiceSceneFactory;
import com.altoukhov.frankfurterdesktop.service.FrankfurterService;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.controlsfx.dialog.ExceptionDialog;

public final class FrankfurterDesktopGUI extends Application {

    private static final String WINDOW_TITLE = "Frankfurter Desktop";
    private static final Image WINDOW_ICON = new Image("/icons/exchange-rate.png");

    private static final String INPUT_ERROR_MESSAGE = "Invalid input. Please make sure you filled out the form correctly.";
    private static final String SERVICE_ERROR_MESSAGE =
            "A service error has occurred. Please make sure you have a reliable network connection. If the error persists, please " +
            "contact the developer or open an issue on GitHub.";
    private static final String UNKNOWN_EXCEPTION_DIALOG_HEADER = "Oops!";
    private static final String UNKNOWN_EXCEPTION_DIALOG_BODY =
            "An unforeseen error has occurred. Please contact the developer or open an issue on GitHub.";

    private final Alert inputErrorAlert;
    private final Alert serviceErrorAlert;

    public FrankfurterDesktopGUI() {
        inputErrorAlert = createAlert(Alert.AlertType.ERROR, INPUT_ERROR_MESSAGE);
        serviceErrorAlert = createAlert(Alert.AlertType.ERROR, SERVICE_ERROR_MESSAGE);
    }

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
        HttpHost host = new HttpHost(URIScheme.HTTPS.getId(), "api.frankfurter.app");
        FrankfurterService service = new FrankfurterService(host);
        Scene scene = FrankfurterServiceSceneFactory.create(service);
        stage.setScene(scene);
        stage.show();
    }

    private Alert createAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    private void createAndShowExceptionDialog(Exception e) {
        ExceptionDialog dialog = new ExceptionDialog(e);
        dialog.setHeaderText(UNKNOWN_EXCEPTION_DIALOG_HEADER);
        dialog.setContentText(UNKNOWN_EXCEPTION_DIALOG_BODY);
        dialog.showAndWait();
    }
}
