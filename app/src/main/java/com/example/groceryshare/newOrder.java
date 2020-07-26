package com.example.groceryshare;

import java.util.ArrayList;
import java.util.Date;

public class newOrder {
    Date dateFulfilled;
    String orderId, storeName, buyerId, shopperId, receiptCopy, address, paymentType, otherInfo, status;
    ArrayList<GroceryItem> shoppingList;


    public newOrder(String orderId, String status, Date dateFulfilled, String storeName, String buyerId, String shopperId, String receiptCopy, ArrayList<GroceryItem> shoppingList, String address, String paymentType, String otherInfo){
        this.orderId = orderId;
        this.status = status;
        this.dateFulfilled = dateFulfilled;
        this.storeName = storeName;
        this.buyerId = buyerId;
        this.shopperId = shopperId;
        this.receiptCopy = receiptCopy;
        this.shoppingList = shoppingList;
        this.address = address;
        this.paymentType = paymentType;
        this.otherInfo = otherInfo;
    }

    public String getOrderId(){
        return orderId;
    }
    public String getStatus() {
        return status;
    }
    public Date getDateFulfilled(){
        return dateFulfilled;
    }
    public String getStoreName(){
        return storeName;
    }
    public String getBuyerId(){
        return buyerId;
    }
    public String getShopperId(){
        return shopperId;
    }
    public String getReceiptCopy(){
        return receiptCopy;
    }
    public ArrayList<GroceryItem> getShoppingList(){
        return shoppingList;
    }
    public String getAddress(){
        return address;
    }
    public String getPaymentType(){
        return paymentType;
    }
    public String getOtherInfo(){
        return otherInfo;
    }
    public newOrder(){}
}
