package com.example.groceryshare;

public class orderData {
    String name, buyerID, storeName, distance, orderID;

    public orderData(String name, String buyerID, String storeName, String distance, String orderID) {
        this.name = name;
        this.buyerID = buyerID;
        this.storeName = storeName;
        this.distance = distance;
        this.orderID = orderID;
    }

}