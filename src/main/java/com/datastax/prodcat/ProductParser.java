package com.datastax.prodcat;

import com.datastax.prodcat.dao.ProductDao;
import com.datastax.prodcat.product.Product;
import com.datastax.prodcat.product.ProductCassandra;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.util.concurrent.ListenableFuture;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.*;


public class ProductParser {
    public static List<String> convertToArrayList (String delimitedString){
        List<String> arrayList = new ArrayList<String>(Arrays.asList(delimitedString.split(";")));
        return arrayList;
    }

    public static void  main(String[] args) throws IOException, InterruptedException {

        String[] contactPts = {"jphmac","127.0.0.1"};
        ProductDao dao = new ProductDao(contactPts);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Product.class)
                .withHeader()
                .withoutQuoteChar()
                .withColumnSeparator('\t');

        File csvFile = new File("src/main/data/files.index.csv"); // or from String, URL etc
        MappingIterator<Product> it = mapper.readerFor(Product.class).with(schema).readValues(csvFile);


        Queue<ListenableFuture> requestQueue = new LinkedList<>();
        int rateLimit = 200;

        while (it.hasNext()) {
            Product row = it.next();
            ProductCassandra product = new ProductCassandra();

            product.setProduct_id(row.getProductId());
            product.setPath(row.getPath());
            product.setUpdated(row.getUpdated());
            product.setSupplier_id(row.getSupplierId());
            product.setProd_id(row.getProdId());
            product.setCatid(row.getCatId());
            product.setM_prod_id(row.getmProdId());

            String eanUPC = row.getEanUpc();
            List<String> eanUPCList = convertToArrayList(eanUPC);

            product.setEan_upc(eanUPCList);
            product.setOn_market(row.getOnMarket());

            String countryMarket = row.getCountryMarket();
            List<String> countryMarketList = convertToArrayList(countryMarket);

            product.setCountry_market(countryMarketList);
            product.setModel_name(row.getModelName());
            product.setProduct_view(row.getProductView());
            product.setHigh_pic(row.getHighPic());
            product.setHigh_pic_size(row.getHighPicSize());
            product.setHigh_pic_width(row.getHighPicWidth());
            product.setHigh_pic_height(row.getHighPicHeight());
            product.setM_supplier_id(row.getmSupplierId());
            product.setM_supplier_name(row.getmSupplierName());

            String eanUPCApproved = row.getEanUpcIsApproved();
            List<String> eanUPCApprovedList = convertToArrayList(eanUPCApproved);
            product.setEan_upc_is_approved(eanUPCApprovedList);

            product.setLimited(row.getLimited());
            product.setDate_Added(row.getDateAdded());

            if (requestQueue.size() >= rateLimit) {
                ListenableFuture<ResultSet> future = requestQueue.remove();
                future.wait();
            }

            ListenableFuture<ResultSet> requestFuture = dao.insertProductAsync(product);
            requestQueue.add(requestFuture);
        }

        while(requestQueue.size() >= 0) {
            ListenableFuture<Result> future = requestQueue.remove();
            future.wait();
        }
    }
}

