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

package com.altoukhov.frankfurterdesktop.gui.util.action;

import com.altoukhov.frankfurterdesktop.gui.control.CurrencyComboBoxFactory;
import com.altoukhov.frankfurterdesktop.gui.notification.Notification;
import com.altoukhov.frankfurterdesktop.model.Currency;
import com.altoukhov.frankfurterdesktop.model.CurrencyRegistry;
import com.altoukhov.frankfurterdesktop.service.FrankfurterService;
import com.altoukhov.frankfurterdesktop.service.ServiceException;
import com.altoukhov.frankfurterdesktop.util.function.ExceptionHandler;

import java.util.Objects;
import java.util.Set;

public final class CurrencySyncAction implements Action {

    private final FrankfurterService service;
    private final ExceptionHandler<ServiceException> serviceExceptionHandler;
    private final Action postSyncNotification;

    public CurrencySyncAction(FrankfurterService service,
                              ExceptionHandler<ServiceException> serviceExceptionHandler,
                              Action postSyncNotification) {
        this.service = Objects.requireNonNull(service, "Provided service is null");
        this.serviceExceptionHandler = Objects.requireNonNull(serviceExceptionHandler, "Provided service exception handler is null");
        this.postSyncNotification = Objects.requireNonNull(postSyncNotification, "Provided action is null");
    }

    public CurrencySyncAction(FrankfurterService service, Notification notification, Action postSyncNotification) {
        this(service, notification.toExceptionHandler(), postSyncNotification);
    }

    public CurrencySyncAction(FrankfurterService service, ExceptionHandler<ServiceException> serviceExceptionHandler) {
        this(service, serviceExceptionHandler, Action.EMPTY);
    }

    public CurrencySyncAction(FrankfurterService service, Notification notification) {
        this(service, notification, Action.EMPTY);
    }

    @Override
    public void run() {
        try {
            Set<Currency> currencies = service.serveCurrencies();
            CurrencyRegistry.GLOBAL.update(currencies);
            CurrencyComboBoxFactory.reloadSharedItems();
            postSyncNotification.run();
        } catch (ServiceException e) {
            serviceExceptionHandler.handle(e);
        }
    }
}
