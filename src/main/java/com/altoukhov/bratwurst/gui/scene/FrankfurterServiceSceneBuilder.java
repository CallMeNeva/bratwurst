// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package com.altoukhov.bratwurst.gui.scene;

import com.altoukhov.bratwurst.gui.control.EntitySheetGroup;
import com.altoukhov.bratwurst.gui.control.ExchangesView;
import com.altoukhov.bratwurst.gui.control.FontAwesomeButtonBuilder;
import com.altoukhov.bratwurst.gui.notification.Notification;
import com.altoukhov.bratwurst.gui.sheet.AbstractExchangesRequestSheet;
import com.altoukhov.bratwurst.gui.sheet.HTTPHostSheet;
import com.altoukhov.bratwurst.gui.sheet.HistoricalExchangesRequestSheet;
import com.altoukhov.bratwurst.gui.sheet.InvalidSheetInputException;
import com.altoukhov.bratwurst.gui.sheet.LatestExchangesRequestSheet;
import com.altoukhov.bratwurst.gui.sheet.TimeSeriesExchangesRequestSheet;
import com.altoukhov.bratwurst.gui.util.action.Action;
import com.altoukhov.bratwurst.gui.util.action.CurrencySyncAction;
import com.altoukhov.bratwurst.model.Exchange;
import com.altoukhov.bratwurst.service.FrankfurterService;
import com.altoukhov.bratwurst.service.ServiceException;
import com.altoukhov.bratwurst.service.request.AbstractExchangeDataRequest;
import com.altoukhov.bratwurst.util.function.ExceptionHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import org.apache.hc.core5.http.HttpHost;
import org.controlsfx.glyphfont.FontAwesome;

import java.util.Objects;
import java.util.Set;

public final class FrankfurterServiceSceneBuilder implements Builder<Scene> {

    /*
     * TODO: Externalize UI strings
     */

    private static final String LATEST_RATES_SHEET_NAME = "Latest";
    private static final String HISTORICAL_RATES_SHEET_NAME = "Historical";
    private static final String TIME_SERIES_RATES_SHEET_NAME = "Time Series";
    private static final String HOST_SHEET_NAME = "Host";

    private static final String ABOUT_BUTTON_TOOLTIP = "About";
    private static final String CLEAR_BUTTON_TOOLTIP = "Clear";
    private static final String CURRENCY_SYNC_BUTTON_TOOLTIP = "Sync Currencies";
    private static final String GO_BUTTON_TOOLTIP = "Go";

    private FrankfurterService service;
    private Action aboutNotification;
    private Action currencySyncNotification;
    private Action hostChangeNotification;
    private ExceptionHandler<ServiceException> serviceExceptionHandler;
    private ExceptionHandler<InvalidSheetInputException> badInputExceptionHandler;

    public FrankfurterServiceSceneBuilder(FrankfurterService service) {
        service(service);
        aboutNotification(Action.EMPTY);
        currencySyncNotification(Action.EMPTY);
        hostChangeNotification(Action.EMPTY);
        serviceExceptionHandler(ExceptionHandler.ignoring());
        badInputExceptionHandler(ExceptionHandler.ignoring());
    }

    public FrankfurterServiceSceneBuilder service(FrankfurterService service) {
        this.service = Objects.requireNonNull(service, "Provided service is null");
        return this;
    }

    public FrankfurterServiceSceneBuilder aboutNotification(Action action) {
        aboutNotification = Objects.requireNonNull(action, "Provided action is null");
        return this;
    }

    public FrankfurterServiceSceneBuilder currencySyncNotification(Action action) {
        currencySyncNotification = Objects.requireNonNull(action, "Provided action is null");
        return this;
    }

    public FrankfurterServiceSceneBuilder hostChangeNotification(Action action) {
        hostChangeNotification = Objects.requireNonNull(action, "Provided action is null");
        return this;
    }

    public FrankfurterServiceSceneBuilder serviceExceptionHandler(ExceptionHandler<ServiceException> handler) {
        serviceExceptionHandler = Objects.requireNonNull(handler, "Provided handler is null");
        return this;
    }

    public FrankfurterServiceSceneBuilder serviceExceptionHandler(Notification notification) {
        Objects.requireNonNull(notification, "Provided notification is null");
        return serviceExceptionHandler(notification.toExceptionHandler());
    }

    public FrankfurterServiceSceneBuilder badInputExceptionHandler(ExceptionHandler<InvalidSheetInputException> handler) {
        badInputExceptionHandler = Objects.requireNonNull(handler, "Provided handler is null");
        return this;
    }

    public FrankfurterServiceSceneBuilder badInputExceptionHandler(Notification notification) {
        Objects.requireNonNull(notification, "Provided notification is null");
        return badInputExceptionHandler(notification.toExceptionHandler());
    }

    @Override
    public Scene build() {
        /*
         * Data view
         */
        ExchangesView view = new ExchangesView();

        /*
         * Sheets
         */
        EntitySheetGroup sheetGroup = new EntitySheetGroup();
        sheetGroup.addSheet(LATEST_RATES_SHEET_NAME, new LatestExchangesRequestSheet());
        sheetGroup.addSheet(HISTORICAL_RATES_SHEET_NAME, new HistoricalExchangesRequestSheet());
        sheetGroup.addSheet(TIME_SERIES_RATES_SHEET_NAME, new TimeSeriesExchangesRequestSheet());
        sheetGroup.addSheet(HOST_SHEET_NAME, new HTTPHostSheet());

        /*
         * Buttons
         */
        Button aboutButton = new FontAwesomeButtonBuilder(FontAwesome.Glyph.INFO_CIRCLE)
                .action(aboutNotification)
                .glyphColor(Color.CORNFLOWERBLUE)
                .tooltipText(ABOUT_BUTTON_TOOLTIP)
                .build();

        Button currencySyncButton = new FontAwesomeButtonBuilder(FontAwesome.Glyph.USD)
                .action(new CurrencySyncAction(service, serviceExceptionHandler, currencySyncNotification))
                .tooltipText(CURRENCY_SYNC_BUTTON_TOOLTIP)
                .build();

        Button clearButton = new FontAwesomeButtonBuilder(FontAwesome.Glyph.REMOVE)
                .action(sheetGroup::clearSelectedSheet)
                .glyphColor(Color.RED)
                .tooltipText(CLEAR_BUTTON_TOOLTIP)
                .build();

        Action goButtonAction = () -> {
            try {
                switch (sheetGroup.getSelectedSheet()) {
                    case AbstractExchangesRequestSheet sheet -> {
                        AbstractExchangeDataRequest request = (AbstractExchangeDataRequest) sheet.submit();
                        Set<Exchange> exchanges = service.serveExchanges(request);
                        view.getItems().setAll(exchanges);
                    }
                    case HTTPHostSheet sheet -> {
                        HttpHost host = sheet.submit();
                        service.setHost(host);
                        hostChangeNotification.run();
                    }
                    case default -> {} /* Should never happen */
                }
            } catch (InvalidSheetInputException e) {
                badInputExceptionHandler.handle(e);
            } catch (ServiceException e) {
                serviceExceptionHandler.handle(e);
            }
        };

        Button goButton = new FontAwesomeButtonBuilder(FontAwesome.Glyph.SEND)
                .action(goButtonAction)
                .glyphColor(Color.LIMEGREEN)
                .tooltipText(GO_BUTTON_TOOLTIP)
                .build();

        /*
         * Toolbar
         */
        Region toolbarDeadSpace = new Region();
        HBox.setHgrow(toolbarDeadSpace, Priority.ALWAYS);
        ToolBar toolbar = new ToolBar(
                aboutButton,
                new Separator(Orientation.VERTICAL),
                toolbarDeadSpace,
                new Separator(Orientation.VERTICAL),
                currencySyncButton,
                clearButton,
                goButton
        );

        /*
         * Root pane
         */
        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(view);
        root.setRight(sheetGroup);

        return new Scene(root);
    }
}
