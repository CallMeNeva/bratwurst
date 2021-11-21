package io.github.altoukhovmax.frankfurterdesktop.service.request;

/*
 * General interface for objects representing data requests.
 */
public sealed interface DataRequest permits AbstractExchangeRatesRequest {

    /*
     * Returns this request object's URI path string representation, i.e. a FooRequest with properties "bar" and "baz"
     * could return the string "foo?bar=hello&baz=world" or something similar. Implementations do not include a leading
     * forward slash character.
     */
    String toURIPath();
}
