package com.altmax.rates.controller;

import com.altmax.rates.model.ExchangeRate;
import com.altmax.rates.service.OpenExchangeRatesService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public final class PrimarySceneController implements Initializable {

    @FXML private TextField appIdField;
    @FXML private TextField currencyCodeField;
    @FXML private Button goButton;
    @FXML private TableView<ExchangeRate> ratesView;

    private OpenExchangeRatesService service;
    private Alert errorAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.appIdField.lengthProperty().addListener((observable, oldValue, newValue) ->
                this.goButton.setDisable(newValue.intValue() == 0 || this.currencyCodeField.getLength() == 0));
        this.currencyCodeField.lengthProperty().addListener((observable, oldValue, newValue) ->
                this.goButton.setDisable(newValue.intValue() == 0 || this.appIdField.getLength() == 0));

        this.service = new OpenExchangeRatesService();
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
        this.errorAlert.setHeaderText(null);
    }

    @FXML
    private void onGoButtonPress(ActionEvent event) {
        try {
            List<ExchangeRate> rates = this.service.fetchLatestRates(this.appIdField.getText(), this.currencyCodeField.getText());
            this.ratesView.getItems().setAll(rates);
        } catch (Exception e) {
            this.errorAlert.setContentText(e.getMessage());
            this.errorAlert.showAndWait();
        }
    }
}
