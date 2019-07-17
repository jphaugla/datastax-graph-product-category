package com.datastax.prodcat.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcecatInterface {
    @JsonProperty("Response")
    private SupplierResponse response;
}
