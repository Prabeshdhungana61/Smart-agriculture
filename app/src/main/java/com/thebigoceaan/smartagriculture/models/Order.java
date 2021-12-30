package com.thebigoceaan.smartagriculture.models;
import com.google.firebase.database.Exclude;
import com.google.type.Date;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;


public class Order implements Serializable {
    @Exclude
            private String key;

    String orderId;
    String orderDate,sellerEmail, buyerEmail,buyerName, buyerProfile,productTitle;

    public Order(String sellerEmail, String buyerEmail, String buyerName, String buyerProfile,String productTitle) {
        this.orderId= UUID.randomUUID().toString();
        this.orderDate= Calendar.getInstance().getTime().toString();
        this.sellerEmail = sellerEmail;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.buyerProfile = buyerProfile;
        this.productTitle = productTitle;
    }
    public Order(){
    }
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerProfile() {
        return buyerProfile;
    }

    public void setBuyerProfile(String buyerProfile) {
        this.buyerProfile = buyerProfile;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
