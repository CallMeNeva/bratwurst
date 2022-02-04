// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.URIScheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractExchangeDataRequest extends AbstractDataRequest {

    private String baseCurrencyCode;
    private List<String> targetCurrencyCodes;
    private double amount;

    protected AbstractExchangeDataRequest(URIScheme scheme,
                                          String hostname,
                                          int port,
                                          String baseCurrencyCode,
                                          List<String> targetCurrencyCodes,
                                          double amount) {
        super(scheme, hostname, port);
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
    protected final Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(3);
        parameters.put("amount", Double.toString(amount));

        if (StringUtils.isNotBlank(baseCurrencyCode)) {
            parameters.put("from", baseCurrencyCode);
        }

        if (ObjectUtils.isNotEmpty(targetCurrencyCodes)) {
            // Null elements result in a malformed request, but that shouldn't ever happen
            String joinedTargets = String.join(",", targetCurrencyCodes);
            parameters.put("to", joinedTargets);
        }

        return parameters;
    }
}
