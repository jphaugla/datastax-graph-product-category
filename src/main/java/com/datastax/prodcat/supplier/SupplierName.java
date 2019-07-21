package com.datastax.prodcat.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplierName {
    @JsonProperty("langid")
    private int langId;

    @JsonProperty("Name")
    private String name;

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
