package com.datastax.prodcat.product;

import com.datastax.driver.mapping.annotations.*;

import java.util.Date;
import java.util.List;

@Table(keyspace = "prodcat", name = "product")
public class ProductCassandra {

    private String path;
    @PartitionKey
    @Column(name="product_id")
    private String product_id;
    private Date updated;
    private String quality;
    private int supplier_id;
    private String prod_id;
    private int catid;
    private String m_prod_id;
    private List<String> ean_upc;
    private int on_market;
    private List<String>country_market;
    private String model_name;

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getM_prod_id() {
        return m_prod_id;
    }

    public void setM_prod_id(String m_prod_id) {
        this.m_prod_id = m_prod_id;
    }

    public List<String> getEan_upc() {
        return ean_upc;
    }

    public void setEan_upc(List<String> ean_upc) {
        this.ean_upc = ean_upc;
    }

    public int getOn_market() {
        return on_market;
    }

    public void setOn_market(int on_market) {
        this.on_market = on_market;
    }

    public List<String> getCountry_market() {
        return country_market;
    }

    public void setCountry_market(List<String> country_market) {
        this.country_market = country_market;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public int getProduct_view() {
        return product_view;
    }

    public void setProduct_view(int product_view) {
        this.product_view = product_view;
    }

    public String getHigh_pic() {
        return high_pic;
    }

    public void setHigh_pic(String high_pic) {
        this.high_pic = high_pic;
    }

    public int getHigh_pic_size() {
        return high_pic_size;
    }

    public void setHigh_pic_size(int high_pic_size) {
        this.high_pic_size = high_pic_size;
    }

    public int getHigh_pic_width() {
        return high_pic_width;
    }

    public void setHigh_pic_width(int high_pic_width) {
        this.high_pic_width = high_pic_width;
    }

    public int getHigh_pic_height() {
        return high_pic_height;
    }

    public void setHigh_pic_height(int high_pic_height) {
        this.high_pic_height = high_pic_height;
    }

    public int getM_supplier_id() {
        return m_supplier_id;
    }

    public void setM_supplier_id(int m_supplier_id) {
        this.m_supplier_id = m_supplier_id;
    }

    public String getM_supplier_name() {
        return m_supplier_name;
    }

    public void setM_supplier_name(String m_supplier_name) {
        this.m_supplier_name = m_supplier_name;
    }

    public List<String> getEan_upc_is_approved() {
        return ean_upc_is_approved;
    }

    public void setEan_upc_is_approved(List<String> ean_upc_is_approved) {
        this.ean_upc_is_approved = ean_upc_is_approved;
    }

    public String getLimited() {
        return Limited;
    }

    public void setLimited(String limited) {
        Limited = limited;
    }

    public Date getDate_Added() {
        return Date_Added;
    }

    public void setDate_Added(Date date_Added) {
        Date_Added = date_Added;
    }

    private int product_view;
    private String high_pic;
    private int high_pic_size;
    private int high_pic_width;
    private int high_pic_height;
    private int m_supplier_id;
    private String m_supplier_name;
    private List<String> ean_upc_is_approved;
    private String Limited;
    private Date Date_Added;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
