package com.altmax.rates.controller;

import com.altmax.rates.api.ExchangeRatesService;
import com.altmax.rates.api.LatestRatesResponseBody;
import com.altmax.rates.model.ExchangeRate;

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
import java.util.stream.Collectors;

public final class MainSceneController implements Initializable {

    @FXML private TextField appIdField;
    @FXML private TextField currencyCodeField;
    @FXML private Button goButton;
    @FXML private TableView<ExchangeRate> ratesTable;

    private Alert errorAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.appIdField.lengthProperty().addListener((observable, oldValue, newValue) ->
                this.goButton.setDisable(newValue.intValue() == 0 || this.currencyCodeField.getLength() == 0));
        this.currencyCodeField.lengthProperty().addListener((observable, oldValue, newValue) ->
                this.goButton.setDisable(newValue.intValue() == 0 || this.appIdField.getLength() == 0));
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
        this.errorAlert.setHeaderText(null);
    }

    @FXML
    private void onGoButtonPress(ActionEvent event) {
        try {
            LatestRatesResponseBody responseBody = ExchangeRatesService.getLatest(this.appIdField.getText(), this.currencyCodeField.getText());
            List<ExchangeRate> exchangeRates = responseBody.getRates().entrySet().stream()
                    .map((e) -> new ExchangeRate(responseBody.getBaseCurrencyCode(), e.getKey(), e.getValue(), responseBody.getTimestamp()))
                    .collect(Collectors.toList());
            this.ratesTable.getItems().setAll(exchangeRates);
        } catch (Exception e) {
            this.errorAlert.setContentText(e.getMessage());
            this.errorAlert.showAndWait();
        }
    }
}
