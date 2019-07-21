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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceIncludeRules getSourceIncludeRules() {
        return sourceIncludeRules;
    }

    public void setSourceIncludeRules(SourceIncludeRules sourceIncludeRules) {
        this.sourceIncludeRules = sourceIncludeRules;
    }

    public DestinationIncludeRules getDestinationIncludeRules() {
        return destinationIncludeRules;
    }

    public void setDestinationIncludeRules(DestinationIncludeRules destinationIncludeRules) {
        this.destinationIncludeRules = destinationIncludeRules;
    }

    public DestinationExcludeRules getDestinationExcludeRules() {
        return destinationExcludeRules;
    }

    public void setDestinationExcludeRules(DestinationExcludeRules destinationExcludeRules) {
        this.destinationExcludeRules = destinationExcludeRules;
    }
}
