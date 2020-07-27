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

public class PastTripsShopper extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference databaseOrders;

    ArrayList<String> s1 = new ArrayList<String>();
    ArrayList<String> s2 = new ArrayList<String>();
    ArrayList<String> s3 = new ArrayList<String>();
    ArrayList<String> s4 = new ArrayList<String>();
    String userID;
    String complete = "Complete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_trips_shopper);


        recyclerView = findViewById(R.id.recyclerView);

        final Past_trips_shopper_adapter myAdapter = new Past_trips_shopper_adapter(this, s1, s2, s3, s4, userID);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase.getInstance().getReference().child("Orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot snapshots = dataSnapshot.child("Orders");
                        for (DataSnapshot snapshot : snapshots.getChildren()) {
                            if (snapshot.child("shopperId").getValue(String.class) != null && snapshot.child("Status").getValue(String.class) != null) {
                                if (snapshot.child("shopperId").getValue(String.class).equals(userID) && snapshot.child("Status").getValue(String.class).equals(complete)) {
                                    String buyerID = snapshot.child("buyerId").getValue(String.class);
                                    String storeName = snapshot.child("storeName").getValue(String.class);
                                    String addressShopper = snapshot.child("address").getValue(String.class);
                                    String orderID = snapshot.child("orderId").getValue(String.class);
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
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    private void getAddress(DataSnapshot dataSnapshot, String buyerID, String storeName, String addressShopper, String orderID, Past_trips_shopper_adapter myAdapter) throws IOException, JSONException {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Buyers");
        String addressBuyer = "";
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("buyerID").getValue(String.class).equals(buyerID)) {
                addressBuyer = snapshot.child("address").getValue(String.class);
            }
        }
        String distance = DistanceCalculator.main(addressBuyer, addressShopper);

        myAdapter.addOrder(buyerID, storeName, distance, orderID);
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

    public void goDetails(String orderID) {
        Intent intent = new Intent(this, OrderFulfillShopper.class);
        intent.putExtra("ORDER_ID", orderID);
        startActivity(intent);
    }
}