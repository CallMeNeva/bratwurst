package com.altmax.rates.controller;

import com.altmax.rates.model.ExchangeRate;
import com.altmax.rates.service.ExchangeRatesService;
import com.altmax.rates.service.OpenExchangeRatesService;
import com.altmax.rates.service.ServiceException;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public final class PrimarySceneController implements Initializable {

    private static final String[] ALL_TARGETS_PLACEHOLDER = new String[]{};

    private @FXML TextField appIdField;
    private @FXML TextField baseCurrencyCodeField;
    private @FXML TextField targetCurrenciesCodesField;
    private @FXML CheckBox allRatesCheckBox;
    private @FXML CheckBox alternativeCurrenciesCheckBox;
    private @FXML DatePicker datePicker;
    private @FXML CheckBox latestRatesCheckBox;
    private @FXML Button goButton;
    private @FXML TableView<ExchangeRate> ratesView;

    private final ExchangeRatesService service;
    private final Alert confirmationAlert;
    private final Alert errorAlert;

    public PrimarySceneController() {
        this.service = new OpenExchangeRatesService();
        this.confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        this.confirmationAlert.setHeaderText(null);
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
        this.errorAlert.setHeaderText(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* On ANY input change of the following controls toggle the "Go" button depending on overall input validity */
        this.appIdField.lengthProperty().addListener(this::setGoButtonAvailabilityToInputValidity);
        this.baseCurrencyCodeField.lengthProperty().addListener(this::setGoButtonAvailabilityToInputValidity);
        this.targetCurrenciesCodesField.lengthProperty().addListener(this::setGoButtonAvailabilityToInputValidity);
        this.allRatesCheckBox.selectedProperty().addListener(this::setGoButtonAvailabilityToInputValidity);
        this.datePicker.getEditor().lengthProperty().addListener(this::setGoButtonAvailabilityToInputValidity);
        this.latestRatesCheckBox.selectedProperty().addListener(this::setGoButtonAvailabilityToInputValidity);

        this.allRatesCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> this.targetCurrenciesCodesField.setDisable(newValue));
        this.latestRatesCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> this.datePicker.setDisable(newValue));
    }

    @FXML
    private void onGoButtonPress(ActionEvent event) {
        try {
            if (this.showInputConfirmationAlert()) {
                String appId = this.appIdField.getText();
                String baseCode = this.baseCurrencyCodeField.getText();
                boolean includeAlternatives = this.alternativeCurrenciesCheckBox.isSelected();
                String[] targets = (this.allRatesCheckBox.isSelected() ?
                        ALL_TARGETS_PLACEHOLDER :
                        this.targetCurrenciesCodesField.getText().split(","));
                List<ExchangeRate> rates = (this.latestRatesCheckBox.isSelected() ?
                        this.service.fetchRates(appId, baseCode, includeAlternatives, targets) :
                        this.service.fetchRates(appId, baseCode, this.datePicker.getValue(), includeAlternatives, targets));
                this.ratesView.getItems().setAll(rates);
            }
        } catch (ServiceException e) {
            this.errorAlert.setContentText(e.getMessage());
            this.errorAlert.showAndWait();
        }
    }

    private boolean isInputValid() {
        return (!this.appIdField.getText().isEmpty()) &&
               (!this.baseCurrencyCodeField.getText().isEmpty()) &&
               (!this.targetCurrenciesCodesField.getText().isEmpty() || this.allRatesCheckBox.isSelected()) &&
               (!this.datePicker.getEditor().getText().isEmpty() || this.latestRatesCheckBox.isSelected());
    }

    /* Has the interface of a ChangeListener to allow passing it to controls as a property change listener */
    private <T> void setGoButtonAvailabilityToInputValidity(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        this.goButton.setDisable(!isInputValid());
    }

    private boolean showInputConfirmationAlert() {
        String confirmationMessage =
                "Confirm the following request:\n" +
                "Base currency: " + this.baseCurrencyCodeField.getText() + "\n" +
                "Target currencies: " + (this.allRatesCheckBox.isSelected() ?
                        "All\n" :
                        (this.targetCurrenciesCodesField.getText() + " (make sure they're in a valid CSV format)\n")) +
                "Alternative currencies: " + (this.alternativeCurrenciesCheckBox.isSelected() ?
                        "Yes\n" :
                        "No\n") +
                "Date: " + (this.latestRatesCheckBox.isSelected() ?
                        "Latest" :
                        this.datePicker.getEditor().getText());
        this.confirmationAlert.setContentText(confirmationMessage);
        Optional<ButtonType> confirmationAlertPressedButton = this.confirmationAlert.showAndWait();
        return confirmationAlertPressedButton.isPresent() && confirmationAlertPressedButton.get().equals(ButtonType.OK);
    }
}
