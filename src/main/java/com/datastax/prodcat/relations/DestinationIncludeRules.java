package com.datastax.prodcat.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinationIncludeRules {

    @JsonProperty("Rule")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Rule> rules;

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        if (rules != null)
            this.rules = rules;
    }
}
