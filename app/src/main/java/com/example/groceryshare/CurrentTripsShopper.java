package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrentTripsShopper extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseOrders;

    ArrayList<String> s1 = new ArrayList<String>();
    ArrayList<String> s2 = new ArrayList<String>();
    ArrayList<String> s3 = new ArrayList<String>();
    ArrayList<String> s4 = new ArrayList<String>();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_trips_shopper);

        recyclerView = findViewById(R.id.recyclerView);

        final Current_trips_shopper_adapter myAdapter = new Current_trips_shopper_adapter(this, s1, s2, s3, s4, userID);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase.getInstance().getReference().child("Orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.child("shopperId").getValue(String.class) != null) {
                                if (snapshot.child("shopperId").getValue(String.class).equals(userID)) {
                                    String buyerID = snapshot.child("buyerId").getValue(String.class);
                                    String storeName = snapshot.child("storeName").getValue(String.class);
                                    String address = snapshot.child("address").getValue(String.class);
                                    String orderID = snapshot.child("orderId").getValue(String.class);
                                    myAdapter.addOrder(buyerID, storeName, address, orderID);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



    }
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }
    public void deleteShopper(String orderID) {
        databaseOrders.child(orderID).child("shopperId").setValue(null);
    }
}