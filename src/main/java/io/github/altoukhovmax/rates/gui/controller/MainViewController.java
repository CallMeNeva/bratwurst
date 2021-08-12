package io.github.altoukhovmax.rates.gui.controller;

import io.github.altoukhovmax.rates.domain.entity.Currency;
import io.github.altoukhovmax.rates.domain.entity.ExchangeRate;
import io.github.altoukhovmax.rates.domain.service.ExchangeRatesService;
import io.github.altoukhovmax.rates.domain.service.FrankfurterService;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public final class MainViewController implements Initializable {

    private static final String[] ALL_TARGETS = {};

    private @FXML TableView<ExchangeRate> dataView;
    private @FXML TableColumn<ExchangeRate, String> baseColumn;
    private @FXML TableColumn<ExchangeRate, String> targetColumn;
    private @FXML TableColumn<ExchangeRate, Number> rateColumn;
    private @FXML TableColumn<ExchangeRate, LocalDate> dateColumn;

    private @FXML ComboBox<Currency> baseSelector;
    private @FXML CheckComboBox<Currency> targetSelector;
    private @FXML CheckBox allRatesCheckbox;

    private @FXML RadioButton specificDatePanelToggle;
    private @FXML RadioButton dateSeriesPanelToggle;
    private @FXML GridPane specificDatePanel;
    private @FXML GridPane dateSeriesPanel;

    private @FXML DatePicker specificDatePicker;
    private @FXML CheckBox latestDateCheckbox;

    private @FXML DatePicker seriesStartDatePicker;
    private @FXML DatePicker seriesEndDatePicker;
    private @FXML CheckBox upToPresentCheckbox;

    private final Alert errorAlert;

    private final ExchangeRatesService service;

    public MainViewController() {
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(null);
        service = new FrankfurterService();
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resourceBundle) {
        final Optional<List<Currency>> fetchedCurrencies = service.fetchAvailableCurrencies();
        fetchedCurrencies.ifPresentOrElse(currencies -> {
            baseSelector.getItems().setAll(currencies);
            targetSelector.getItems().setAll(currencies);
        }, () -> {
            errorAlert.setContentText("Failed to fetch the list of available currencies. Without it the " +
                                      "application won't function correctly. Please check your network " +
                                      "connection and restart the application.");
            errorAlert.showAndWait();
        });

        StringConverter<Currency> currencyConverter = new StringConverter<>() {
            @Override
            public String toString(Currency currency) {
                return currency == null ? null : currency.displayName();
            }
            @Override
            public Currency fromString(String s) {
                return null;
            }
        };
        baseSelector.setConverter(currencyConverter);
        targetSelector.setConverter(currencyConverter);
        allRatesCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> targetSelector.setDisable(newValue));

        specificDatePanelToggle.selectedProperty().addListener((observable, oldValue, newValue) -> specificDatePanel.setVisible(newValue));
        dateSeriesPanelToggle.selectedProperty().addListener((observable, oldValue, newValue) -> dateSeriesPanel.setVisible(newValue));
        specificDatePanelToggle.setSelected(true);

        latestDateCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> specificDatePicker.setDisable(newValue));

        upToPresentCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> seriesEndDatePicker.setDisable(newValue));

        baseColumn.setCellValueFactory(cellDataFeatures -> new ReadOnlyStringWrapper(cellDataFeatures.getValue().baseCurrencyCode()));
        targetColumn.setCellValueFactory(cellDataFeatures -> new ReadOnlyStringWrapper(cellDataFeatures.getValue().targetCurrencyCode()));
        rateColumn.setCellValueFactory(cellDataFeatures -> new ReadOnlyDoubleWrapper(cellDataFeatures.getValue().factor()));
        dateColumn.setCellValueFactory(cellDateFeatures -> new ReadOnlyObjectWrapper<>(cellDateFeatures.getValue().publicationDate()));
    }

    ///////////////////////////
    //    Input Validation   //
    ///////////////////////////

    private boolean isCurrencyInputValid() {
        return (baseSelector.getValue() != null) &&
               (!targetSelector.getCheckModel().getCheckedIndices().isEmpty() || allRatesCheckbox.isSelected());
    }

    private boolean isTimeFrameInputValid() {
        if (specificDatePanelToggle.isSelected()) {
            LocalDate date = specificDatePicker.getValue();
            return (date != null) || latestDateCheckbox.isSelected();
        }
        if (dateSeriesPanelToggle.isSelected()) {
            LocalDate startDate = seriesStartDatePicker.getValue();
            LocalDate endDate = seriesEndDatePicker.getValue();
            return (startDate != null) && (!startDate.isAfter(LocalDate.now())) &&
                   ((endDate != null) && (endDate.isAfter(startDate)) || upToPresentCheckbox.isSelected());
        }
        throw new IllegalStateException("Invalid user input state"); // Should not (and probably never will) happen
    }

    ////////////////////////////
    //    Button Callbacks    //
    ////////////////////////////

    @FXML
    private void onGoButtonPress() {
        if (isCurrencyInputValid() && isTimeFrameInputValid()) {
            String base = baseSelector.getValue().code();
            String[] targets = allRatesCheckbox.isSelected() ?
                    ALL_TARGETS :
                    targetSelector.getCheckModel().getCheckedIndices().stream()
                            .map(idx -> targetSelector.getItems().get(idx).code())
                            .toArray(String[]::new);
            Optional<List<ExchangeRate>> fetchedExchangeRates = specificDatePanelToggle.isSelected() ?
                    (latestDateCheckbox.isSelected() ? service.fetchExchangeRates(base, targets) : service.fetchExchangeRates(base, specificDatePicker.getValue(), targets)) :
                    service.fetchExchangeRates(base, seriesStartDatePicker.getValue(), (upToPresentCheckbox.isSelected() ? null : seriesEndDatePicker.getValue()), targets);
            fetchedExchangeRates.ifPresentOrElse(rates -> dataView.getItems().setAll(rates), () -> {
                errorAlert.setContentText("Failed to fetch the requested exchange rates. This could happen due to " +
                                          "an internal service error, a client-side error, or simply a bad network " +
                                          "connection. If the problem persists, please contact your local administrator.");
                errorAlert.showAndWait();
            });
        } else {
            errorAlert.setContentText("Please make sure you filled out all the necessary fields in the request form correctly.");
            errorAlert.showAndWait();
        }
    }
}
