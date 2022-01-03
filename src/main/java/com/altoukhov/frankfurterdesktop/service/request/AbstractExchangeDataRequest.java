/*
 * Copyright 2021, 2022 Maxim Altoukhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.altoukhov.frankfurterdesktop.service.request;

import com.altoukhov.frankfurterdesktop.model.Currency;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractExchangeDataRequest extends AbstractDataRequest {

    private Currency base;
    private Collection<Currency> targets;
    private double amount;

    /* NOTE: Null base and null/empty targets collection are allowed and represent default values (picked by the service) */
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
    protected final Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(3);

        parameters.put("amount", Double.toString(amount));

        if (Objects.nonNull(base)) {
            parameters.put("from", base.code());
        }

        if (Objects.nonNull(targets)) {
            String joinedTargets = targets.stream()
                    .filter(Objects::nonNull) /* Just in case */
                    .map(Currency::code)
                    .collect(Collectors.joining(","));

            if (!joinedTargets.isEmpty()) {
                parameters.put("to", joinedTargets);
            }
        }

        return parameters;
    }
}
