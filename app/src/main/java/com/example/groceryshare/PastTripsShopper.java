package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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

public class PastTripsShopper extends AppCompatActivity {

    DatabaseReference databaseOrders;
    RecyclerView recyclerView;
    LinearLayout emptyView;
    String userID;
    String name, buyerID, storeName, addressShopper, addressBuyer, orderID;
    String complete = "Complete";
    Button settingsShopper;
    private static final String TAG = "PastTripsShopper";

    private Past_trips_shopper_adapter myAdapter;
    private List orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_trips_shopper);

        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");
        settingsShopper = findViewById(R.id.settingsShopper);
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");

        emptyView = findViewById(R.id.recycler_empty_view);

        recyclerView = findViewById(R.id.recyclerViewDefault);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new Past_trips_shopper_adapter(this, orders);
        recyclerView.setAdapter(myAdapter);

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshots = dataSnapshot.child("Orders");
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                    if (snapshot.child("shopperId").getValue(String.class) != null) {
                        if (snapshot.child("shopperId").getValue(String.class).equals(userID) && snapshot.child("status").getValue(String.class).equals(complete)) {
                            buyerID = snapshot.child("buyerId").getValue(String.class);
                            storeName = snapshot.child("storeName").getValue(String.class);
                            addressShopper = snapshot.child("address").getValue(String.class);
                            orderID = snapshot.child("orderId").getValue(String.class);
                            try {
                                getBuyer(dataSnapshot, buyerID, storeName, addressShopper, orderID, myAdapter);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (orders.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    Log.e(TAG, "Order is Empty");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        settingsShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                settingsShopper();
            }
        });
    }

    private void getBuyer(DataSnapshot dataSnapshot, String buyerID, String storeName, String addressShopper, String orderID, Past_trips_shopper_adapter myAdapter) throws IOException, JSONException {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Buyers");
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("buyerID").getValue(String.class).equals(buyerID)) {
                name = snapshot.child("firstName").getValue(String.class);
                addressBuyer = snapshot.child("address").getValue(String.class);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
        String distance = DistanceCalculator.main(addressBuyer, addressShopper);
        orderData data = new orderData(name, buyerID, storeName, distance, orderID);
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

    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }


    public void goDetails(String orderID){
        Intent intent = new Intent(this, OrderCompleteDetails.class);
        intent.putExtra("ORDER_ID", orderID);
        intent.putExtra("USER_ID",userID);
        startActivity(intent);
    }


    public void settingsShopper() {
        Intent intent = new Intent(this, SettingsShopper.class);
        startActivity(intent);
    }
}