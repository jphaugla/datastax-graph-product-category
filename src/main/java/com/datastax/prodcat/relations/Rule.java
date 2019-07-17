package com.datastax.prodcat.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rule {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Supplier")
    private Supplier supplier;

    @JsonProperty("Prod_id")
    private ProductId productId;

    @JsonProperty("Category")
    private Category category;

    @JsonProperty("Feature")
    private Feature feature;

    @JsonProperty("Start_date")
    private StartDate startDate;

    @JsonProperty("End_date")
    private EndDate endDate;
}
