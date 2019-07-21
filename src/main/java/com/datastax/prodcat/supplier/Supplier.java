package com.datastax.prodcat.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Supplier {

    @JsonProperty("ID")
    private int id;

    @JsonProperty("LogoPic")
    private String logoPic;

    @JsonProperty("LogoPicHeight")
    private int logoPicHeight;

    @JsonProperty("LogoPicWidth")
    private int logoPicWidth;

    @JsonProperty("LogoPicSize")
    private int logoPicSize;

    @JsonProperty("LogoLowPic")
    private String logoLowPic;

    @JsonProperty("LogoLowPicHeight")
    private int logoLowPicHeight;

    @JsonProperty("LogoLowPicWidth")
    private int logoLowPicWidth;

    @JsonProperty("LogoLowSize")
    private int LogoLowSize;

    @JsonProperty("LogoMediumPic")
    private String logoMediumPic;

    @JsonProperty("LogoMediumPicHeight")
    private int logoMediumPicHeight;

    @JsonProperty("LogoMediumPicWidth")
    private int logoMediumPicWidth;

    @JsonProperty("LogoMediumPicSize")
    private int logoMediumPicSize;

    @JsonProperty("LogoHighPic")
    private String logoHighPic;

    @JsonProperty("LogoHighPicHeight")
    private int logoHighPicHeight;

    @JsonProperty("LogoHighPicWidth")
    private int logoHighPicWidth;

    @JsonProperty("LogoHighPicSize")
    private int logoHighPicSize;

    @JsonProperty("LogoOriginal")
    private String logoOriginal;

    @JsonProperty("LogoOriginalSize")
    private int logoOriginalSize;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Sponsor")
    private String sponsor;

    @JsonProperty("Names")
    private List<SupplierName> names;

    @JsonProperty("CustomerService")
    private CustomerService customerService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public int getLogoPicHeight() {
        return logoPicHeight;
    }

    public void setLogoPicHeight(int logoPicHeight) {
        this.logoPicHeight = logoPicHeight;
    }

    public int getLogoPicWidth() {
        return logoPicWidth;
    }

    public void setLogoPicWidth(int logoPicWidth) {
        this.logoPicWidth = logoPicWidth;
    }

    public int getLogoPicSize() {
        return logoPicSize;
    }

    public void setLogoPicSize(int logoPicSize) {
        this.logoPicSize = logoPicSize;
    }

    public String getLogoLowPic() {
        return logoLowPic;
    }

    public void setLogoLowPic(String logoLowPic) {
        this.logoLowPic = logoLowPic;
    }

    public int getLogoLowPicHeight() {
        return logoLowPicHeight;
    }

    public void setLogoLowPicHeight(int logoLowPicHeight) {
        this.logoLowPicHeight = logoLowPicHeight;
    }

    public int getLogoLowPicWidth() {
        return logoLowPicWidth;
    }

    public void setLogoLowPicWidth(int logoLowPicWidth) {
        this.logoLowPicWidth = logoLowPicWidth;
    }

    public int getLogoLowSize() {
        return LogoLowSize;
    }

    public void setLogoLowSize(int logoLowSize) {
        LogoLowSize = logoLowSize;
    }

    public String getLogoMediumPic() {
        return logoMediumPic;
    }

    public void setLogoMediumPic(String logoMediumPic) {
        this.logoMediumPic = logoMediumPic;
    }

    public int getLogoMediumPicHeight() {
        return logoMediumPicHeight;
    }

    public void setLogoMediumPicHeight(int logoMediumPicHeight) {
        this.logoMediumPicHeight = logoMediumPicHeight;
    }

    public int getLogoMediumPicWidth() {
        return logoMediumPicWidth;
    }

    public void setLogoMediumPicWidth(int logoMediumPicWidth) {
        this.logoMediumPicWidth = logoMediumPicWidth;
    }

    public int getLogoMediumPicSize() {
        return logoMediumPicSize;
    }

    public void setLogoMediumPicSize(int logoMediumPicSize) {
        this.logoMediumPicSize = logoMediumPicSize;
    }

    public String getLogoHighPic() {
        return logoHighPic;
    }

    public void setLogoHighPic(String logoHighPic) {
        this.logoHighPic = logoHighPic;
    }

    public int getLogoHighPicHeight() {
        return logoHighPicHeight;
    }

    public void setLogoHighPicHeight(int logoHighPicHeight) {
        this.logoHighPicHeight = logoHighPicHeight;
    }

    public int getLogoHighPicWidth() {
        return logoHighPicWidth;
    }

    public void setLogoHighPicWidth(int logoHighPicWidth) {
        this.logoHighPicWidth = logoHighPicWidth;
    }

    public int getLogoHighPicSize() {
        return logoHighPicSize;
    }

    public void setLogoHighPicSize(int logoHighPicSize) {
        this.logoHighPicSize = logoHighPicSize;
    }

    public String getLogoOriginal() {
        return logoOriginal;
    }

    public void setLogoOriginal(String logoOriginal) {
        this.logoOriginal = logoOriginal;
    }

    public int getLogoOriginalSize() {
        return logoOriginalSize;
    }

    public void setLogoOriginalSize(int logoOriginalSize) {
        this.logoOriginalSize = logoOriginalSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public List<SupplierName> getNames() {
        return names;
    }

    public void setNames(List<SupplierName> names) {
        this.names = names;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
