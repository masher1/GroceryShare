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

import java.util.ArrayList;

public class CurrentTripsShopper extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference databaseOrders;
    DatabaseReference database;

    ArrayList<String> s1 = new ArrayList<String>();
    ArrayList<String> s2 = new ArrayList<String>();
    ArrayList<String> s3 = new ArrayList<String>();
    ArrayList<String> s4 = new ArrayList<String>();
    String userID;
    String name, storeName, distance, orderID, buyerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        database = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_trips_shopper);


        recyclerView = findViewById(R.id.recyclerView);

        final Current_trips_shopper_adapter myAdapter = new Current_trips_shopper_adapter(this, s1, s2, s3, s4, userID);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshots = dataSnapshot.child("Orders");
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                            if(snapshot.child("shopperId").getValue(String.class) != null && snapshot.child("Status").getValue(String.class) == null) {
                                if (snapshot.child("shopperId").getValue(String.class).equals(userID)) {
                                    storeName = snapshot.child("storeName").getValue(String.class);
                                    distance = snapshot.child("address").getValue(String.class);
                                    orderID = snapshot.child("orderId").getValue(String.class);
                                    buyerID = snapshot.child("buyerId").getValue(String.class);
                                    getBuyer(database, dataSnapshot, storeName, orderID, buyerID, myAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



    }

    private void getBuyer(DatabaseReference database, DataSnapshot dataSnapshot, String storeName, String orderId, String buyerId, Current_trips_shopper_adapter myAdapter) {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Buyers");
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("buyerID").getValue(String.class).equals(buyerId)) {
                name = snapshot.child("firstName").getValue(String.class);
            }
        }
        myAdapter.addOrder(name, storeName, distance, orderID);
    }
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }
    public void deleteShopper(String orderID) {
        //databaseOrders.child(orderID).child("shopperId").setValue(null);
        Intent intent = new Intent(this, OrderFulfillShopper.class);
        startActivity(intent);
    }
    public void goDetails(String orderID){
        Intent intent = new Intent(this, OrderFulfillShopper.class);
        intent.putExtra("ORDER_ID", orderID);
        startActivity(intent);
    }
}