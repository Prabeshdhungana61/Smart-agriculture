package com.thebigoceaan.smartagriculture.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Product implements Serializable {
    @Exclude
     private String key;
    private Long productId;
    private String userId, productTitle, productImage, productPrice, productStock, productDescription;

    public Product(Long productId, String userId, String productTitle, String productImage, String productPrice, String productStock, String productDescription) {
        this.productId = productId;
        this.userId = userId;
        this.productTitle = productTitle;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
    }

    public Product(){}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
