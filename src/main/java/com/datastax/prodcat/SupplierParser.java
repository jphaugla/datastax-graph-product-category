package com.datastax.prodcat;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.bind.annotation.XmlSchema;
import java.io.File;
import java.io.IOException;

public class SupplierParser {

    public static void main(String[] args) throws IOException {


        File suppliersList = new File("src/main/data/SuppliersList.xml");
        XmlMapper xmlMapper = new XmlMapper();

        IcecatInterface value = xmlMapper.readValue(suppliersList, IcecatInterface.class);

        System.out.println(value);



    }
}

