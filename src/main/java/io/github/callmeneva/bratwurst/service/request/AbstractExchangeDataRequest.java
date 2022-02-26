// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractExchangeDataRequest implements DataRequest {

    private String baseCurrencyCode;
    private List<String> targetCurrencyCodes;
    private double amount;

    protected AbstractExchangeDataRequest(String baseCurrencyCode, List<String> targetCurrencyCodes, double amount) {
        setBaseCurrencyCode(baseCurrencyCode);
        setTargetCurrencyCodes(targetCurrencyCodes);
        setAmount(amount);
    }

    public final String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public final void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public final List<String> getTargetCurrencyCodes() {
        return targetCurrencyCodes;
    }

    public final void setTargetCurrencyCodes(List<String> targetCurrencyCodes) {
        this.targetCurrencyCodes = targetCurrencyCodes;
    }

    public final double getAmount() {
        return amount;
    }

    public final void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public final Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(3);

        // Since there is no validation on properties, it's very easy to make a request malformed — it's up to the class user to make sure it isn't

        if (baseCurrencyCode != null) {
            parameters.put("from", baseCurrencyCode);
        }

        if ((targetCurrencyCodes != null) && !targetCurrencyCodes.isEmpty()) {
            String joinedTargets = String.join(",", targetCurrencyCodes);
            parameters.put("to", joinedTargets);
        }

        parameters.put("amount", Double.toString(amount));

        return parameters;
    }
}
