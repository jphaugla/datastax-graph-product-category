package com.datastax.prodcat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Supplier {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("LogoPic")
    private String logoPic;

    @JsonProperty("LogoPicHeight")
    private String logoPicHeight;

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
}
