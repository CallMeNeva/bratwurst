package io.github.altoukhovmax.frankfurterdesktop.service.request;

public enum CurrenciesRequest implements DataRequest {
    INSTANCE;

    @Override
    public String toURIPath() {
        return "currencies";
    }
}
