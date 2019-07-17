package com.datastax.prodcat;

import com.datastax.prodcat.relations.RelationIcecatInterface;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;

import java.io.File;
import java.io.IOException;

public class RelationsParser {

    public static void main(String[] args) throws IOException {


        File relationsList = new File("src/main/data/RelationsList.xml");
        XmlMapper xmlMapper = new XmlMapper();

        RelationIcecatInterface value = xmlMapper.readValue(relationsList, RelationIcecatInterface.class);
        System.out.println(value);




    }
}

