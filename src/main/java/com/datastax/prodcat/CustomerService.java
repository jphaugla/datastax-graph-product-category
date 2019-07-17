package com.datastax.prodcat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerService {

    @JsonProperty("AddressDetails")
    private String addressDetails;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Country_Code")
    private String countryCode;

    @JsonProperty("Country_ID")
    private String countryId;

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Website")
    private String website;
}
