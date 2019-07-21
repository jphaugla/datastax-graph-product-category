package com.datastax.prodcat.supplier;

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

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
