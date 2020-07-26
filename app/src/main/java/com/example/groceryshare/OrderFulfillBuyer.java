package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderFulfillBuyer extends AppCompatActivity {
    private Button viewShopInfo;
    private Button viewShopList;
    private Button viewReceipt;
    private Button confirmOrderDone;
    private Button cancelOrder;

    DatabaseReference databaseBuyers;
    DatabaseReference databaseShoppers;
    DatabaseReference databaseOrders;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_fulfill_buyer);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        databaseBuyers = FirebaseDatabase.getInstance().getReference("Buyers");
        databaseShoppers = FirebaseDatabase.getInstance().getReference("Shoppers");
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");

        viewShopInfo = findViewById(R.id.viewShopperInfoBtn);
        viewShopList = findViewById(R.id.viewShoppingListBtn);
        viewReceipt = findViewById(R.id.viewReceiptBtn);
        confirmOrderDone = findViewById(R.id.confirmOrderDoneBtn);
        cancelOrder = findViewById(R.id.cancelOrderBtn);

        viewShopInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillBuyer.this, shopperInfoCurrentOrder.class);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });

        viewShopList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillBuyer.this, viewShoppingListCurrentOrder.class);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        viewReceipt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillBuyer.this, viewReceipt.class);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        confirmOrderDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillBuyer.this, BuyerRatesShopper.class);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });

        cancelOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Orders")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if ((snapshot.child("buyerId").getValue(String.class) != null) && (snapshot.child("buyerId").getValue(String.class).equals(userID))) {
                                        if (snapshot.child("shopperId").getValue(String.class) == null) {
                                            snapshot.getRef().removeValue();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


                Intent intent = new Intent(OrderFulfillBuyer.this, PendingActivity.class);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });

    }

    public void goBack(View v) {
        Intent intent = new Intent(this, PendingActivity.class);
        startActivity(intent);
    }
}