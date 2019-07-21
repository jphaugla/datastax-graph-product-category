package com.datastax.prodcat.supplier;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Supplier> getSuppliersList() {
        return suppliersList;
    }

    public void setSuppliersList(List<Supplier> suppliersList) {
        this.suppliersList = suppliersList;
    }
}
