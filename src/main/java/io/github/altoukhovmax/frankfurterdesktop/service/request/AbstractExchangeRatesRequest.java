/*
 * Copyright 2021 Maxim Altoukhov
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

package io.github.altoukhovmax.frankfurterdesktop.service.request;

import io.github.altoukhovmax.frankfurterdesktop.model.Currency;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Base class for exchange rates requests.
 */
public abstract class AbstractExchangeRatesRequest extends AbstractDataRequest {

    private Currency base;
    private Collection<Currency> targets;

    /**
     * Initializes the base and targets of this exchange rates request. Either of those properties may be left unspecified (i.e. null for
     * base and null/empty for targets) to use the service's default values when it processes the request. This applies to setters as well.
     *
     * @param base this request's base currency (may be null)
     * @param targets a collection of this request's target currencies (may be null or empty)
     */
    protected AbstractExchangeRatesRequest(Currency base, Collection<Currency> targets) {
        setBase(base);
        setTargets(targets);
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

    @Override
    protected final Map<String, String> getParameters() {
        /*
         * FIXME: Current impl is (probably?) ineffective. For instance, if both base and targets are unspecified, we could avoid
         *        constructing a HashMap and return super's params instead.
         */
        Map<String, String> parameters = new HashMap<>(2);

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
