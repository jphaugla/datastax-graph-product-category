package com.datastax.prodcat.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;

@JsonPropertyOrder({
        "path",
        "product_id",
        "updated",
        "quality",
        "supplier_id",
        "prod_id",
        "catid",
        "m_prod_id",
        "ean_upc",
        "on_market",
        "country_market",
        "model_name",
        "product_view",
        "high_pic",
        "high_pic_size",
        "high_pic_width",
        "high_pic_height",
        "m_supplier_id",
        "m_supplier_name",
        "ean_upc_is_approved",
        "Limited",
        "Date_Added"
})
public class Product {
    @JsonProperty("path")
    private String path;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date updated;

    @JsonProperty("quality")
    private String quality;

    @JsonProperty("supplier_id")
    private int supplierId;

    @JsonProperty("prod_id")
    private String prodId;

    @JsonProperty("catid")
    private int catId;

    @JsonProperty("m_prod_id")
    private String mProdId;

    @JsonProperty("ean_upc")
    private String eanUpc;

    @JsonProperty("on_market")
    private int onMarket;

    @JsonProperty("country_market")
    private String countryMarket;

    @JsonProperty("model_name")
    private String modelName;

    @JsonProperty("product_view")
    private int productView;

    @JsonProperty("high_pic")
    private String highPic;

    @JsonProperty("high_pic_size")
    private int highPicSize;

    @JsonProperty("high_pic_width")
    private int highPicWidth;

    @JsonProperty("high_pic_height")
    private int highPicHeight;

    @JsonProperty("m_supplier_id")
    private int mSupplierId;

    @JsonProperty("m_supplier_name")
    private String mSupplierName;

    @JsonProperty("ean_upc_is_approved")
    private String eanUpcIsApproved;

    @JsonProperty("Limited")
    private String limited;

    @JsonProperty("Date_Added")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date dateAdded;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getmProdId() {
        return mProdId;
    }

    public void setmProdId(String mProdId) {
        this.mProdId = mProdId;
    }

    public String getEanUpc() {
        return eanUpc;
    }

    public void setEanUpc(String eanUpc) {
        this.eanUpc = eanUpc;
    }

    public int getOnMarket() {
        return onMarket;
    }

    public void setOnMarket(int onMarket) {
        this.onMarket = onMarket;
    }

    public String getCountryMarket() {
        return countryMarket;
    }

    public void setCountryMarket(String countryMarket) {
        this.countryMarket = countryMarket;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getProductView() {
        return productView;
    }

    public void setProductView(int productView) {
        this.productView = productView;
    }

    public String getHighPic() {
        return highPic;
    }

    public void setHighPic(String highPic) {
        this.highPic = highPic;
    }

    public int getHighPicSize() {
        return highPicSize;
    }

    public void setHighPicSize(int highPicSize) {
        this.highPicSize = highPicSize;
    }

    public int getHighPicWidth() {
        return highPicWidth;
    }

    public void setHighPicWidth(int highPicWidth) {
        this.highPicWidth = highPicWidth;
    }

    public int getHighPicHeight() {
        return highPicHeight;
    }

    public void setHighPicHeight(int highPicHeight) {
        this.highPicHeight = highPicHeight;
    }

    public int getmSupplierId() {
        return mSupplierId;
    }

    public void setmSupplierId(int mSupplierId) {
        this.mSupplierId = mSupplierId;
    }

    public String getmSupplierName() {
        return mSupplierName;
    }

    public void setmSupplierName(String mSupplierName) {
        this.mSupplierName = mSupplierName;
    }

    public String getEanUpcIsApproved() {
        return eanUpcIsApproved;
    }

    public void setEanUpcIsApproved(String eanUpcIsApproved) {
        this.eanUpcIsApproved = eanUpcIsApproved;
    }

    public String getLimited() {
        return limited;
    }

    public void setLimited(String limited) {
        this.limited = limited;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}

