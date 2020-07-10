package com.example.groceryshare;

public class GroceryItem {
    private String itemName;
    private String quantity;
    private String brand;

    public GroceryItem(){

    }

    public GroceryItem(String itemName, String quantity, String brand){
        this.itemName = itemName;
        this.quantity = quantity;
        this.brand = brand;
    }

    public String getItemName(){
        return itemName;
    }

    public String getQuantity(){
        return quantity;
    }

    public String getBrand(){
        return brand;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public void setQuantity(String quantity){
        this.quantity = quantity;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }
}
