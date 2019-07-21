package com.datastax.prodcat.relations;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;
import java.util.List;

@Table(keyspace = "prodcat", name = "product_cross_sell")
public class ProductCrossSell {
    @PartitionKey
    @Column(name="source_product_id")
    private String sourceProductId;

    @ClusteringColumn
    @Column(name="cross_sell_product_id")
    private String crossSellProductId;


    public String getSourceProductId() {
        return sourceProductId;
    }

    public void setSourceProductId(String sourceProductId) {
        this.sourceProductId = sourceProductId;
    }

    public String getCrossSellProductId() {
        return crossSellProductId;
    }

    public void setCrossSellProductId(String crossSellProductId) {
        this.crossSellProductId = crossSellProductId;
    }
}
