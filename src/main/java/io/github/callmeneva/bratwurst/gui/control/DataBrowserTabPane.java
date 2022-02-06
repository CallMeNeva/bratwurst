// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.browser.DataBrowser;
import io.github.callmeneva.bratwurst.gui.util.Resettable;
import io.github.callmeneva.bratwurst.l10n.Localization;
import io.github.callmeneva.bratwurst.service.DataFetchFailureException;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.lang3.Validate;

public class DataBrowserTabPane extends TabPane implements Resettable {

    public DataBrowserTabPane() {
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    public void addBrowser(DataBrowser<?, ?> dataBrowser, String l10nKey) {
        Validate.notNull(dataBrowser);

        String title = Localization.getString(l10nKey);
        Tab tab = new Tab(title, dataBrowser);

        getTabs().add(tab);
    }

    public void submitSelected() throws DataFetchFailureException {
        getSelectedBrowser().submitRequest();
    }

    private DataBrowser<?, ?> getSelectedBrowser() {
        Tab selectedTab = getSelectionModel().getSelectedItem();
        if (selectedTab.getContent() instanceof DataBrowser<?,?> browser) {
            return browser;
        }
        throw new IllegalStateException();
    }

    @Override
    public void reset() {
        getSelectedBrowser().reset();
    }
}
