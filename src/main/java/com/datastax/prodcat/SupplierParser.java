package com.datastax.prodcat;

import com.datastax.prodcat.dao.ProductDao;
import com.datastax.prodcat.dao.SupplierDao;
import com.datastax.prodcat.product.ProductCassandra;
import com.datastax.prodcat.supplier.IcecatInterface;
import com.datastax.prodcat.supplier.Supplier;
import com.datastax.prodcat.supplier.SupplierCassandra;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.util.concurrent.ListenableFuture;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SupplierParser {

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException, ExecutionException {
        String[] contactPts = {"127.0.0.1"};
        SupplierDao dao = new SupplierDao(contactPts);

        File suppliersList = new File("src/main/data/SuppliersList.xml");
        XmlMapper xmlMapper = new XmlMapper();

        IcecatInterface suppliers = xmlMapper.readValue(suppliersList, IcecatInterface.class);

        Queue<ListenableFuture> requestQueue = new LinkedList<>();
        int rateLimit = 200;

        long before = System.currentTimeMillis();

        for (Supplier row : suppliers.getResponse().getSuppliersList()){
            SupplierCassandra supplier = new SupplierCassandra();

            supplier.setId(row.getId());

            supplier.setLogoPic(row.getLogoPic());
            supplier.setLogoPicHeight(row.getLogoPicHeight());
            supplier.setLogoPicWidth(row.getLogoPicWidth());
            supplier.setLogoPicSize(row.getLogoPicSize());

            supplier.setLogoLowPic(row.getLogoLowPic());
            supplier.setLogoLowPicHeight(row.getLogoLowPicHeight());
            supplier.setLogoLowPicWidth(row.getLogoLowPicWidth());
            supplier.setLogoLowSize(row.getLogoLowSize());

            supplier.setLogoMediumPic(row.getLogoMediumPic());
            supplier.setLogoMediumPicHeight(row.getLogoMediumPicHeight());
            supplier.setLogoMediumPicWidth(row.getLogoMediumPicWidth());
            supplier.setLogoMediumPicSize(row.getLogoMediumPicSize());

            supplier.setLogoHighPic(row.getLogoHighPic());
            supplier.setLogoHighPicHeight(row.getLogoHighPicHeight());
            supplier.setLogoHighPicWidth(row.getLogoHighPicWidth());
            supplier.setLogoHighPicSize(row.getLogoHighPicSize());

            supplier.setLogoOriginal(row.getLogoOriginal());
            supplier.setLogoOriginalSize(row.getLogoOriginalSize());

            supplier.setName(row.getName());
            supplier.setSponsor(row.getSponsor());
//            supplier.setNames(row.getNames());

            if (requestQueue.size() >= rateLimit) {
                ListenableFuture<ResultSet> future = requestQueue.remove();
                future.get(10, TimeUnit.SECONDS);

            }

            ListenableFuture<ResultSet> requestFuture = dao.insertSupplierAsync(supplier);
            requestQueue.add(requestFuture);
        }

        while(requestQueue.size() > 0) {
            ListenableFuture<Result> future = requestQueue.remove();
            future.get(10, TimeUnit.SECONDS);

        }

        long after = System.currentTimeMillis();


        System.out.println(suppliers.getResponse().getSuppliersList().size() + " Rows Written in " + (after - before)  + "ms");


    }
}

