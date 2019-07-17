package com.datastax.prodcat.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Relation {

    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "SourceIncludeRules")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SourceIncludeRules sourceIncludeRules;

    @JsonProperty("DestinationIncludeRules")
    @JacksonXmlElementWrapper(useWrapping = false)
    private DestinationIncludeRules destinationIncludeRules;

    @JsonProperty("DestinationExcludeRules")
    @JacksonXmlElementWrapper(useWrapping = false)
    private DestinationExcludeRules destinationExcludeRules;

}
