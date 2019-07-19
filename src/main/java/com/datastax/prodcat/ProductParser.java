package com.datastax.prodcat;

import com.datastax.prodcat.dao.ProductDao;
import com.datastax.prodcat.product.Product;
import com.datastax.prodcat.product.ProductCassandra;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductParser {
    public static List<String> convertToArrayList (String delimitedString){
        List<String> arrayList = new ArrayList<String>(Arrays.asList(delimitedString.split(";")));
        return arrayList;
    }

    public static void  main(String[] args) throws IOException {
        String[] contactPts = {"jphmac","127.0.0.1"};
        ProductDao dao = new ProductDao(contactPts);
        ProductCassandra productCassandra= new ProductCassandra();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Product.class)
                .withHeader()
                .withoutQuoteChar()
                .withColumnSeparator('\t');

        File csvFile = new File("src/main/data/files.index.csv"); // or from String, URL etc
        MappingIterator<Product> it = mapper.readerFor(Product.class).with(schema).readValues(csvFile);


        while (it.hasNext()) {
            Product row = it.next();
            System.out.println(row.getCatId());
            productCassandra.setProduct_id(row.getProductId());
            productCassandra.setPath(row.getPath());

            productCassandra.setUpdated(row.getUpdated());
            productCassandra.setSupplier_id(row.getSupplierId());
            productCassandra.setProd_id(row.getProdId());
            productCassandra.setCatid(row.getCatId());

            productCassandra.setM_prod_id(row.getmProdId());
            String eanUPC = row.getEanUpc();
            List<String> eanUPCList = convertToArrayList(eanUPC);
            productCassandra.setEan_upc(eanUPCList);
            productCassandra.setOn_market(row.getOnMarket());
            String countryMarket = row.getCountryMarket();
            List<String> countryMarketList = convertToArrayList(countryMarket);
            productCassandra.setCountry_market(countryMarketList);
            productCassandra.setModel_name(row.getModelName());

            productCassandra.setProduct_view(row.getProductView());
            productCassandra.setHigh_pic(row.getHighPic());
            productCassandra.setHigh_pic_size(row.getHighPicSize());
            productCassandra.setHigh_pic_width(row.getHighPicWidth());
            productCassandra.setHigh_pic_height(row.getHighPicHeight());
            productCassandra.setM_supplier_id(row.getmSupplierId());
            productCassandra.setM_supplier_name(row.getmSupplierName());
            String eanUPCApproved = row.getEanUpcIsApproved();
            List<String> eanUPCApprovedList = convertToArrayList(eanUPCApproved);
            productCassandra.setEan_upc_is_approved(eanUPCApprovedList);
            productCassandra.setLimited(row.getLimited());
            productCassandra.setDate_Added(row.getDateAdded());

            dao.insertProductAsync(productCassandra);
            // TODO: Split Country list by ; and load into List
            // TODO: Write Async to DSE
        }
    }
}

