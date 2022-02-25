// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.browser.AbstractDataBrowser;
import io.github.callmeneva.bratwurst.gui.util.Resettable;
import io.github.callmeneva.bratwurst.l10n.Localization;
import io.github.callmeneva.bratwurst.service.DataFetchFailureException;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Objects;

public class DataBrowserTabPane extends TabPane implements Resettable {

    public DataBrowserTabPane() {
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    public void addBrowser(AbstractDataBrowser<?, ?> dataBrowser, String l10nPropertyName) {
        Objects.requireNonNull(dataBrowser, "Browser must not be null");

        String title = Localization.get(l10nPropertyName);
        getTabs().add(new Tab(title, dataBrowser));
    }

    public void fetchWithSelected() throws DataFetchFailureException {
        getSelectedBrowser().fetch();
    }

    @Override
    public void reset() {
        AbstractDataBrowser<?, ?> selectedBrowser = getSelectedBrowser();
        if (selectedBrowser instanceof Resettable resettableBrowser) {
            resettableBrowser.reset();
        }
    }

    private AbstractDataBrowser<?, ?> getSelectedBrowser() {
        Tab selectedTab = getSelectionModel().getSelectedItem();
        if (selectedTab.getContent() instanceof AbstractDataBrowser<?, ?> browser) {
            return browser;
        }
        throw new IllegalStateException("DataBrowserTabPane should only contain tabs with AbstractDataBrowser as their content");
    }
}
