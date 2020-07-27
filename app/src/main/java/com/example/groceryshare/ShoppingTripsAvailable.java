package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ShoppingTripsAvailable extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseOrders;

    ArrayList<String> s1 = new ArrayList<String>();
    ArrayList<String> s2 = new ArrayList<String>();
    ArrayList<String> s3 = new ArrayList<String>();
    ArrayList<String> s4 = new ArrayList<String>();
    String userID;
    String buyerID, storeName, addressShopper, orderID;


    //newCode started
    private Shopping_trips_available_adapter myAdapter;
    private List orders =new ArrayList<>();
    //newCode ended


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_trips_available);

        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        recyclerView = findViewById(R.id.recyclerView);
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");

        //newCode started
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        myAdapter = new Shopping_trips_available_adapter(orders);
        //newCode ended

//        final Shopping_trips_available_adapter myAdapter = new Shopping_trips_available_adapter(this, s1, s2, s3, s4, userID);

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshots = dataSnapshot.child("Orders");
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                    if (snapshot.child("shopperId").getValue(String.class) == null) {
                        buyerID = snapshot.child("buyerId").getValue(String.class);
                        storeName = snapshot.child("storeName").getValue(String.class);
                        addressShopper = snapshot.child("address").getValue(String.class);
                        orderID = snapshot.child("orderId").getValue(String.class);
                        try {
                            getAddress(dataSnapshot, buyerID, storeName, addressShopper, orderID, myAdapter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getAddress(DataSnapshot dataSnapshot, String buyerID, String storeName, String addressShopper, String orderID, Shopping_trips_available_adapter myAdapter) throws IOException, JSONException {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Buyers");
        String addressBuyer = "";
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("buyerID").getValue(String.class).equals(buyerID)) {
                addressBuyer = snapshot.child("address").getValue(String.class);
            }
        }
        String distance = DistanceCalculator.main(addressBuyer, addressShopper);
        buyerID = buyerID.substring(0, Math.min(buyerID.length(), 15));
        orderData data = new orderData(buyerID, storeName, distance, orderID);
        orders.add(data);
        recyclerView.setAdapter(myAdapter);
        sortAndSubmit(orders);
    }

    private void sortAndSubmit(List orders) {
        Collections.sort(orders, new Comparator<orderData>() {
            @Override
            public int compare(orderData o1, orderData o2) {
                return o1.distance.compareTo(o2.distance);
            }
        });
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }

    public void addShopper(String orderID) {
        databaseOrders.child(orderID).child("shopperId").setValue(userID);
    }

}