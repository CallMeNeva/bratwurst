/*
 * Copyright 2022 Maxim Altoukhov
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

package com.altoukhov.frankfurterdesktop.gui.notification;

import com.altoukhov.frankfurterdesktop.gui.util.action.Action;
import com.altoukhov.frankfurterdesktop.util.function.ExceptionHandler;
import javafx.scene.control.Alert;

public final class Notification extends Alert implements Action {

    private static final String SUCCESSFUL_CURRENCY_SYNC_TEXT = "Currencies synced successfully.";
    private static final String FAILED_INITIAL_CURRENCY_SYNC_TEXT =
            """
            Failed to perform initial currency sync. You can still do this later manually using the "Sync Currencies" button.
            """;

    private static final String HOST_CHANGE_TEXT = "Host changed successfully.";
    private static final String ABOUT_TEXT = "Soon (TM).";
    private static final String BAD_INPUT_TEXT = "Invalid input. Please make sure you filled out the form correctly.";
    private static final String SERVICE_ERROR_TEXT =
            """
            A service error has occurred. Please make sure you have a working network connection. If the error persists, please contact 
            the developer or open an issue on GitHub.
            """;

    public Notification(AlertType alertType, String contentText) {
        super(alertType, contentText);
        setHeaderText(null);
    }

    public static Notification successfulCurrencySync() {
        return new Notification(AlertType.INFORMATION, SUCCESSFUL_CURRENCY_SYNC_TEXT);
    }

    public static Notification failedInitialCurrencySync() {
        return new Notification(AlertType.WARNING, FAILED_INITIAL_CURRENCY_SYNC_TEXT);
    }

    public static Notification hostChange() {
        return new Notification(AlertType.INFORMATION, HOST_CHANGE_TEXT);
    }

    public static Notification about() {
        return new Notification(AlertType.INFORMATION, ABOUT_TEXT);
    }

    public static Notification badSheetInput() {
        return new Notification(AlertType.ERROR, BAD_INPUT_TEXT);
    }

    public static Notification serviceError() {
        return new Notification(AlertType.ERROR, SERVICE_ERROR_TEXT);
    }

    @Override
    public void run() {
        show();
    }

    public <E extends Exception> ExceptionHandler<E> toExceptionHandler() {
        return exception -> run();
    }
}
