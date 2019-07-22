package com.datastax.prodcat;

import com.datastax.prodcat.product.Product;
import com.datastax.prodcat.relations.*;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RelationsParser {

    public static void main(String[] args) throws IOException {

        File relationsList = new File("src/main/data/RelationsList.xml");
        XmlMapper xmlMapper = new XmlMapper();

        RelationIcecatInterface relations = xmlMapper.readValue(relationsList, RelationIcecatInterface.class);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Product.class)
                .withHeader()
                .withoutQuoteChar()
                .withColumnSeparator('\t');

        File csvFile = new File("src/main/data/files.index.csv"); // or from String, URL etc
        MappingIterator<Product> products = mapper.readerFor(Product.class).with(schema).readValues(csvFile);

        HashMap<String, List<Product>> productCategories = new HashMap<>();

        // Group products by category id
        for(final Product product : products.readAll()) {
            if(productCategories.containsKey(Integer.toString(product.getCatId()))){
                productCategories.get(Integer.toString(product.getCatId())).add(product);
            } else {
                List<Product> newCategory = new ArrayList<>();
                newCategory.add(product);
                productCategories.put(Integer.toString(product.getCatId()), newCategory);
            }
        }

        List<ProductCrossSell> productCrossSellList = new ArrayList<>();

        // For each relation group
        for(RelationGroup relationGroup : relations.getRelationGroup()){
            if(relationGroup.getRelations() != null) {
                // For all the relations in the group
                for(Relation relation : relationGroup.getRelations()) {
                    if (relation.getDestinationIncludeRules() != null) {
                        // Determine the category of products that this rule applies to
                        List<Rule> destinationRules = relation.getDestinationIncludeRules().getRules();
                        if (destinationRules.size() > 0 && destinationRules.get(0).getCategory() != null){
                            String category = destinationRules.get(0).getCategory().getValue();
                            List<Product> destinationProducts = productCategories.get(category);
                            // Look up the products in the specified category
                            if (destinationProducts != null) {
                                // Generate the combination of products in the specified category and
                                // the products specified as SourceInclude Rules
                                for(Rule source : relation.getSourceIncludeRules().getRules()) {
                                    for(Product destination : destinationProducts) {
                                        ProductCrossSell productCrossSell = new ProductCrossSell();
                                        productCrossSell.setSourceProductId(source.getProductId().getValue());
                                        productCrossSell.setCrossSellProductId(Integer.toString(destination.getProductId()));

                                        productCrossSellList.add(productCrossSell);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        System.out.println(productCategories);
//        System.out.println("hello here");



    }
}

