package pkg.Models;

import pkg.Entities.Products;

public class ComparedItem {

    private String productID;
    private String productName;
    private long price;
    private String productImages;
    private String descriptions;
    private String feature;
    private String brand;
    private String category;
    private String type;

    public ComparedItem() {
    }

    public ComparedItem(Products item) {
        this.productID = new String(item.getProductID());
        this.productName = new String(item.getProductName());
        this.price = item.getPrice();
        this.productImages = new String(item.getProductImages());
        this.descriptions = new String(item.getDescriptions());
        this.feature = new String(item.getFeature());
        this.brand = new String(item.getBrandID().getBrandName());
        this.category = new String(item.getCategoryID().getCategoryName());
        this.type = new String(item.getTypeID().getTypeName());
    }

    public String getProductID() {
        return this.productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getProductImages() {
        return this.productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public String getDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
