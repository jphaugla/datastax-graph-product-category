package com.datastax.prodcat.dao;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.prodcat.supplier.SupplierCassandra;
import com.google.common.util.concurrent.ListenableFuture;

import static com.datastax.driver.mapping.Mapper.Option.saveNullFields;

public class SupplierDao {

    private Session session;
    private Mapper<SupplierCassandra> SupplierMapper;
    public  SupplierDao(String[] contactPoints){
        Cluster cluster = Cluster.builder().addContactPoints(contactPoints).build();
        this.session = cluster.connect();
        SupplierMapper = new MappingManager(this.session).mapper(SupplierCassandra.class);
        SupplierMapper.setDefaultSaveOptions(saveNullFields(false));
    }

    public ListenableFuture insertSupplierAsync(SupplierCassandra SupplierCassandra) {
        return SupplierMapper.saveAsync(SupplierCassandra);
    }

}
