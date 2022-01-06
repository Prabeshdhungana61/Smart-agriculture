package com.thebigoceaan.smartagriculture.models;

public class Sale {
    int totalSale=0, totalOrders=0;

    public Sale(int totalSale, int totalOrders, int completedOrders, int pendingOrders) {
        this.totalSale = totalSale;
        this.totalOrders = totalOrders;

    }
    public Sale(){};

    public int getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(int totalSale) {
        this.totalSale = totalSale;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
    @Override
    public String toString() {
        return "Sale{" +
                "totalSale=" + totalSale +
                ", totalOrders=" + totalOrders +
                '}';
    }
}
