package com.datastax.prodcat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class SupplierResponse {

    @JsonProperty("Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd HH:mm:ss yyyy")
    private Date date;

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Request_ID")
    private String requestId;

    @JsonProperty("Status")
    private int status;

    @JsonProperty("SuppliersList")
    private List<Supplier> suppliersList;
}
