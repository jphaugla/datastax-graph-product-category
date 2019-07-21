package com.datastax.prodcat.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcecatInterface {
    @JsonProperty("Response")
    private SupplierResponse response;

    public SupplierResponse getResponse() {
        return response;
    }

    public void setResponse(SupplierResponse response) {
        this.response = response;
    }
}
