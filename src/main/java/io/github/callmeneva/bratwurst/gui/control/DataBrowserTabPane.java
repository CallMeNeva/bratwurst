// SPDX-FileCopyrightText: Copyright 2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.gui.control;

import io.github.callmeneva.bratwurst.gui.browser.AbstractDataBrowser;
import io.github.callmeneva.bratwurst.gui.util.Resettable;
import io.github.callmeneva.bratwurst.l10n.Localization;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;
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

    public AbstractDataBrowser<?, ?> getSelectedBrowser() {
        Tab selectedTab = getSelectionModel().getSelectedItem();
        if (selectedTab.getContent() instanceof AbstractDataBrowser<?, ?> browser) {
            return browser;
        }
        throw new IllegalStateException("Selected tab's content is not an AbstractDataBrowser");
    }

    public List<? extends AbstractDataBrowser<?, ?>> getBrowsers() {
        return getTabs().stream()
                .filter(tab -> tab.getContent() instanceof AbstractDataBrowser<?, ?>)
                .map(tab -> (AbstractDataBrowser<?, ?>) tab.getContent())
                .toList();
    }

    @Override
    public void reset() {
        AbstractDataBrowser<?, ?> selectedBrowser = getSelectedBrowser();
        if (selectedBrowser instanceof Resettable resettableBrowser) {
            resettableBrowser.reset();
        }
    }
}
