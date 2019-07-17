package com.datastax.prodcat.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

//@JacksonXmlRootElement(localName = "ICECAT-interface")
public class RelationIcecatInterface {

    @JacksonXmlProperty(localName = "RelationGroup")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<RelationGroup> RelationGroup;

}
