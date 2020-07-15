package com.example.groceryshare;

import java.util.ArrayList;
import java.util.Date;

public class NewOrder {
    private String orderId;
    private Date datePlaced;
    private Date dateFulfilled;
    private Date dateBy;
    private String storeName;
    private String buyerId;
    private String shopperId;
    private String receiptCopy; //??
    private ArrayList<GroceryItem> shoppingList;
    private String address;

    public NewOrder(String orderId, Date datePlaced, Date dateFulfilled, Date dateBy, String storeName, String buyerId, String shopperId, String receiptCopy, ArrayList<GroceryItem> shoppingList, String address){
        this.orderId = orderId;
        this.datePlaced = datePlaced;
        this.dateFulfilled = dateFulfilled;
        this.dateBy = dateBy;
        this.storeName = storeName;
        this.buyerId = buyerId;
        this.shopperId = shopperId;
        this.receiptCopy = receiptCopy;
        this.shoppingList = shoppingList;
        this.address = address;
    }

    public String getOrderId(){
        return orderId;
    }

    public Date getDatePlaced(){
        return datePlaced;
    }

    public Date getDateFulfilled(){
        return dateFulfilled;
    }

    public Date getDateBy(){
        return dateBy;
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




}
