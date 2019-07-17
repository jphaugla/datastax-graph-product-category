package com.datastax.prodcat.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplierName {
    @JsonProperty("langid")
    private int langId;

    @JsonProperty("Name")
    private String name;
}
