package com.example.groceryshare;

public class orderData {
    String buyerID, storeName, distance, orderID;

    public orderData(String buyerID, String storeName, String distance, String orderID) {
        this.buyerID = buyerID;
        this.storeName = storeName;
        this.distance = distance;
        this.orderID = orderID;
    }

}