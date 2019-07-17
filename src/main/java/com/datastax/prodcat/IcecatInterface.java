package com.datastax.prodcat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcecatInterface {
    @JsonProperty("Response")
    private SupplierResponse response;
}
