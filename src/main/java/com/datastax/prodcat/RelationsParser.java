package com.datastax.prodcat;

import com.datastax.prodcat.dao.ProductCrossSellDao;
import com.datastax.prodcat.dao.SupplierDao;
import com.datastax.prodcat.product.Product;
import com.datastax.prodcat.relations.*;
import com.datastax.prodcat.supplier.Supplier;
import com.datastax.prodcat.supplier.SupplierCassandra;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.google.common.util.concurrent.ListenableFuture;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RelationsParser {

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException, ExecutionException {

        File relationsList = new File("src/main/data/RelationsList.xml");
        XmlMapper xmlMapper = new XmlMapper();

        String[] contactPts = {"127.0.0.1"};
        ProductCrossSellDao dao = new ProductCrossSellDao(contactPts);

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
//                                        if(productLookUp.containsKey(source.getProductId().getValue()) &&
//                                                productLookUp.containsKey(destination.getProductId())){
                                            ProductCrossSell productCrossSell = new ProductCrossSell();
                                            productCrossSell.setSourceProductId(source.getProductId().getValue());
                                            productCrossSell.setCrossSellProductId(destination.getProdId());

                                            productCrossSellList.add(productCrossSell);
//                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        Queue<ListenableFuture> requestQueue = new LinkedList<>();
        int rateLimit = 200;

        long before = System.currentTimeMillis();

        for (ProductCrossSell productCrossSell : productCrossSellList){

            if (requestQueue.size() >= rateLimit) {
                ListenableFuture<ResultSet> future = requestQueue.remove();
                future.get(10, TimeUnit.SECONDS);

            }

            ListenableFuture<ResultSet> requestFuture = dao.insertProductCrossSellAsync(productCrossSell);
            requestQueue.add(requestFuture);
        }

        while(requestQueue.size() > 0) {
            ListenableFuture<Result> future = requestQueue.remove();
            future.get(10, TimeUnit.SECONDS);

        }

        long after = System.currentTimeMillis();


        System.out.println(productCrossSellList.size() + " Rows Written in " + (after - before)  + "ms");



    }
}

