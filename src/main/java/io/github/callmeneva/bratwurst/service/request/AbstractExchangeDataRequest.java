// SPDX-FileCopyrightText: Copyright 2021-2022 Maxim Altoukhov
// SPDX-License-Identifier: MIT

package io.github.callmeneva.bratwurst.service.request;

import io.github.callmeneva.bratwurst.model.Currency;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractExchangeDataRequest implements DataRequest {

    private Currency base;
    private Collection<Currency> targets;
    private double amount;

    protected AbstractExchangeDataRequest(Currency base, Collection<Currency> targets, double amount) {
        setBase(base);
        setTargets(targets);
        setAmount(amount);
    }

    public final Currency getBase() {
        return base;
    }

    public final void setBase(Currency base) {
        this.base = base;
    }

    public final Collection<Currency> getTargets() {
        return targets;
    }

    public final void setTargets(Collection<Currency> targets) {
        this.targets = targets;
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
        parameters.put("amount", Double.toString(amount));

        if (base != null) {
            parameters.put("from", base.code());
        }

        if (targets != null) {
            String joinedTargets = targets.stream()
                    .filter(Objects::nonNull) // Just to be safe
                    .map(Currency::code)
                    .collect(Collectors.joining(","));

            if (!joinedTargets.isEmpty()) {
                parameters.put("to", joinedTargets);
            }
        }

        return parameters; // Should this be wrapped in a read-only view?
    }
}
