package com.datastax.prodcat.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceIncludeRules {

    @JsonProperty("Rule")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Rule> rules;
}
