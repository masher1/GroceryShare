package com.example.groceryshare;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class GroceryItem {
    private String itemName;
    private String quantity;
    private String brand;
    public ArrayList<GroceryItem> listRes;
    DatabaseReference databaseReference;

    public GroceryItem(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child("-MCxPNk7PS-P7p6VXm5c").child("shoppingList");

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

    public ArrayList<GroceryItem> getallItems() {
        listRes = new ArrayList<GroceryItem>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataValues : dataSnapshot.getChildren()) {
                    GroceryItem groceryItem = dataValues.getValue(GroceryItem.class);
                    listRes.add(groceryItem);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
        return listRes;
    }


    @Override
    public String toString() {
        return ("Item Name:"+this.getItemName()+
                " Quantity: "+ this.getQuantity() +
                " Brand: "+ this.getBrand());
    }
}
