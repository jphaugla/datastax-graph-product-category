package com.datastax.prodcat;

import com.datastax.prodcat.dao.ProductDao;
import com.datastax.prodcat.product.ProductCassandra;
import com.datastax.prodcat.supplier.IcecatInterface;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class SupplierParser {

    public static void main(String[] args) throws IOException {
        String[] contactPts = {"jphmac","127.0.0.1"};
        ProductDao dao = new ProductDao(contactPts);
        ProductCassandra productCassandra= new ProductCassandra();

        File suppliersList = new File("src/main/data/SuppliersList.xml");
        XmlMapper xmlMapper = new XmlMapper();

        try {
            IcecatInterface value = xmlMapper.readValue(suppliersList, IcecatInterface.class);
            System.out.println(value);

            // TODO: Write async to DSE

        } catch(Exception ex) {
            System.out.println(ex);
        }




    }
}

