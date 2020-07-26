package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


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

public class PendingActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference database;

    ArrayList<String> s1 = new ArrayList<String>();
    ArrayList<String> s2 = new ArrayList<String>();
    ArrayList<String> s3 = new ArrayList<String>();

    String storeName;
    String orderId;
    String shopperId;
    String name;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        database = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_activity);

        recyclerView = findViewById(R.id.recyclerView);

        final pendingAdapter myAdapter = new pendingAdapter(this, s1, s2, s3);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshots = dataSnapshot.child("Orders");
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                    if (snapshot.child("buyerId").getValue(String.class).equals(user.getUid())) {
                        storeName = snapshot.child("storeName").getValue(String.class);
                        orderId = snapshot.child("orderId").getValue(String.class);
                        shopperId = snapshot.child("shopperId").getValue(String.class);
                        getBuyer(database, dataSnapshot, storeName, orderId, shopperId, myAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getBuyer(DatabaseReference database, DataSnapshot dataSnapshot, String storeName, String orderId, String shopperId, pendingAdapter myAdapter) {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Shoppers");
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("shopperID").getValue(String.class).equals(shopperId)) {
                name = snapshot.child("firstName").getValue(String.class);
            }
        }
        myAdapter.addOrder(storeName, orderId, name);
    }


    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }

    public void goDetails(String orderId) {
        Intent intent = new Intent(this, OrderFulfillBuyer.class);
        intent.putExtra("ORDER_ID", orderId);
        startActivity(intent);
    }
}