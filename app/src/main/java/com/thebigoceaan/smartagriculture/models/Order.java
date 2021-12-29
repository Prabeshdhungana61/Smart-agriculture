package com.thebigoceaan.smartagriculture.models;
import java.util.UUID;


public class Order {

    String orderId;
    String sellerEmail, buyerEmail,buyerName, buyerProfile;

    public Order(String sellerEmail, String buyerEmail, String buyerName, String buyerProfile) {
        this.orderId= UUID.randomUUID().toString();
        this.sellerEmail = sellerEmail;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.buyerProfile = buyerProfile;
    }
    public Order(){
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
}
