package com.datastax.prodcat.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.prodcat.relations.ProductCrossSell;
import com.datastax.prodcat.supplier.SupplierCassandra;
import com.google.common.util.concurrent.ListenableFuture;

import static com.datastax.driver.mapping.Mapper.Option.saveNullFields;

public class ProductCrossSellDao {

    private Session session;
    private Mapper<ProductCrossSell> productCrossSellMapper;
    public ProductCrossSellDao(String[] contactPoints){
        Cluster cluster = Cluster.builder().addContactPoints(contactPoints).build();
        this.session = cluster.connect();
        productCrossSellMapper = new MappingManager(this.session).mapper(ProductCrossSell.class);
        productCrossSellMapper.setDefaultSaveOptions(saveNullFields(false));
    }

    public ListenableFuture insertProductCrossSellAsync(ProductCrossSell productCrossSell) {
        return productCrossSellMapper.saveAsync(productCrossSell);
    }

}
