// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.util.action;

import com.altoukhov.bratwurst.gui.control.CurrencyComboBoxFactory;
import com.altoukhov.bratwurst.gui.notification.Notification;
import com.altoukhov.bratwurst.model.Currency;
import com.altoukhov.bratwurst.model.CurrencyRegistry;
import com.altoukhov.bratwurst.service.FrankfurterService;
import com.altoukhov.bratwurst.service.ServiceException;
import com.altoukhov.bratwurst.util.function.ExceptionHandler;

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
