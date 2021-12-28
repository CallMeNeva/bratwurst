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

package com.altoukhov.frankfurterdesktop.gui.scene;

import com.altoukhov.frankfurterdesktop.gui.control.CurrencyComboBoxFactory;
import com.altoukhov.frankfurterdesktop.gui.control.EntitySheetGroup;
import com.altoukhov.frankfurterdesktop.gui.control.ExchangeRatesView;
import com.altoukhov.frankfurterdesktop.gui.control.FontAwesomeButtonBuilder;
import com.altoukhov.frankfurterdesktop.gui.dialog.AboutDialog;
import com.altoukhov.frankfurterdesktop.gui.sheet.HistoricalExchangeRatesRequestSheet;
import com.altoukhov.frankfurterdesktop.gui.sheet.InvalidSheetInputException;
import com.altoukhov.frankfurterdesktop.gui.sheet.LatestExchangeRatesRequestSheet;
import com.altoukhov.frankfurterdesktop.model.ExchangeRate;
import com.altoukhov.frankfurterdesktop.service.FrankfurterService;
import com.altoukhov.frankfurterdesktop.service.ServiceException;
import com.altoukhov.frankfurterdesktop.service.request.AbstractExchangeRatesRequest;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;

import java.util.Objects;
import java.util.Set;

public final class FrankfurterServiceSceneFactory {

    private static final String LATEST_RATES_SHEET_NAME = "Latest";
    private static final String HISTORICAL_RATES_SHEET_NAME = "Latest";
    private static final String TIME_SERIES_RATES_SHEET_NAME = "Time Series";

    public static Scene create(FrankfurterService service) {
        Objects.requireNonNull(service, "Provided service is null");

        /*
         * Rates view
         */
        ExchangeRatesView view = new ExchangeRatesView();

        /*
         * Request sheets
         */
        EntitySheetGroup sheetSwitcher = new EntitySheetGroup();
        sheetSwitcher.addSheet(LATEST_RATES_SHEET_NAME, new LatestExchangeRatesRequestSheet());
        sheetSwitcher.addSheet(HISTORICAL_RATES_SHEET_NAME, new HistoricalExchangeRatesRequestSheet());
        sheetSwitcher.setSide(Side.RIGHT);

        /*
         * Buttons
         */
        Button goButton = new FontAwesomeButtonBuilder(FontAwesome.Glyph.SEND)
//                .onAction(event -> {
//                    try {
//                        AbstractExchangeRatesRequest request = sheetSwitcher.submitFromSelectedSheetAs(AbstractExchangeRatesRequest.class);
//                        Set<ExchangeRate> exchangeRates = service.fetchExchangeRates(request);
//                        view.getItems().setAll(exchangeRates);
//                    } catch (InvalidSheetInputException e) {
//                        invalidSheetInputExceptionHandler.handle(e);
//                    } catch (ServiceException e) {
//                        serviceExceptionHandler.handle(e);
//                    } catch (Exception e) {
//                        unknownExceptionHandler.handle(e);
//                    }
//                })
                .glyphColor(Color.LIMEGREEN)
                .tooltipText("Go")
                .build();

        Button reloadButton = new FontAwesomeButtonBuilder(FontAwesome.Glyph.EXCHANGE)
                .onAction(event -> {
                    service.updateCurrencyRegistry();
                    CurrencyComboBoxFactory.reloadSharedItems();
                })
                .tooltipText("Reload")
                .build();

        Button clearButton = new FontAwesomeButtonBuilder(FontAwesome.Glyph.REMOVE)
                .onAction(event -> sheetSwitcher.clearSelectedSheet())
                .glyphColor(Color.RED)
                .tooltipText("Clear")
                .build();

        Button infoButton = new FontAwesomeButtonBuilder(FontAwesome.Glyph.INFO_CIRCLE)
                .onAction(event -> new AboutDialog().showAndWait())
                .glyphColor(Color.DODGERBLUE)
                .tooltipText("About")
                .build();

        Region toolbarDeadSpace = new Region();
        HBox.setHgrow(toolbarDeadSpace, Priority.ALWAYS);
        ToolBar bar = new ToolBar(
                infoButton,
                new Separator(Orientation.VERTICAL),
                toolbarDeadSpace,
                new Separator(Orientation.VERTICAL),
                reloadButton,
                clearButton,
                goButton
        );

        BorderPane root = new BorderPane();
        root.setTop(bar);
        root.setCenter(view);
        root.setRight(sheetSwitcher);

        return new Scene(root);
    }
}
