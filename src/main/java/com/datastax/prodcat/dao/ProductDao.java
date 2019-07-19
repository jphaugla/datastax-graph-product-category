package com.datastax.prodcat.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.prodcat.product.ProductCassandra;

import static com.datastax.driver.mapping.Mapper.Option.saveNullFields;

public class ProductDao {

    private Session session;
    private Mapper<ProductCassandra> productMapper;
    public  ProductDao(String[] contactPoints){
        Cluster cluster = Cluster.builder().addContactPoints(contactPoints).build();
        this.session = cluster.connect();
        productMapper = new MappingManager(this.session).mapper(ProductCassandra.class);
        productMapper.setDefaultSaveOptions(saveNullFields(false));
    }

     public void insertProductAsync(ProductCassandra productCassandra) {
        productMapper.save(productCassandra);
     }

}
