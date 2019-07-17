package com.datastax.prodcat;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProductParser {

    public static void main(String[] args) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Product.class)
                .withHeader()
                .withColumnSeparator('\t');

        File csvFile = new File("src/main/data/files.index.csv"); // or from String, URL etc
        MappingIterator<Product> it = mapper.readerFor(Product.class).with(schema).readValues(csvFile);


        while (it.hasNext()) {
            Product row = it.next();
            System.out.println(row.getCatId());

            // TODO: Split Country list by ; and load into List
            // TODO: Write Async to DSE
        }


    }
}

