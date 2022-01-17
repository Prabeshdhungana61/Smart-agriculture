package com.thebigoceaan.smartagriculture.models;

import com.google.firebase.database.Exclude;
import java.io.Serializable;

public class Product implements Serializable {
    @Exclude
     private String key;
    private String productId;
    private String userId, productTitle, productImage, productPrice, productStock, productDescription;
    private String sellerProfile,sellerEmail,sellerMobile;

    public Product(String productId, String userId, String productTitle, String productImage, String productPrice, String productStock
            , String productDescription,
                   String sellerProfile, String sellerEmail, String sellerMobile) {
        this.productId = productId;
        this.userId = userId;
        this.productTitle = productTitle;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.sellerProfile=sellerProfile;
        this.sellerEmail=sellerEmail;
        this.sellerMobile=sellerMobile;
    }

    public Product(String productId, String userId, String productTitle, String productImage, String productPrice, String productStock
            , String productDescription){
        this.productId = productId;
        this.userId = userId;
        this.productTitle = productTitle;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
    }

    public Product(){}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(String sellerProfile) {
        this.sellerProfile = sellerProfile;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerMobile() {
        return sellerMobile;
    }

    public void setSellerMobile(String sellerMobile) {
        this.sellerMobile = sellerMobile;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productTitle='" + productTitle + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productStock='" + productStock + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", sellerProfile='" + sellerProfile + '\'' +
                ", sellerEmail='" + sellerEmail + '\'' +
                '}';
    }
}
