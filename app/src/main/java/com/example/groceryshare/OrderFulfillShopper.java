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

import java.util.ArrayList;

public class OrderFulfillShopper extends AppCompatActivity {
    private Button viewOrderBtnShopper;
    private Button viewShoppingListBtnShopper;
    private Button uploadRcptBtnShopper;
    private Button confirmOrderBtnShopper;
    private Button cancelOrder;

    String orderId, storeName, buyerId, shopperId, receiptCopy, address, paymentType, otherInfo;
    ArrayList<GroceryItem> shoppingList;

    DatabaseReference databaseBuyers;
    DatabaseReference databaseShoppers;
    DatabaseReference databaseOrders;
    FirebaseUser user;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_fulfill_shopper);

        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseBuyers = FirebaseDatabase.getInstance().getReference("Buyers");
        databaseShoppers = FirebaseDatabase.getInstance().getReference("Shoppers");
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");

        /* use findViewById() to get the next Button */
        viewOrderBtnShopper = (Button) findViewById(R.id.orderInfoBtn);
        viewShoppingListBtnShopper = (Button) findViewById(R.id.viewListBtnShopper);
        uploadRcptBtnShopper = (Button) findViewById(R.id.uploadRcptTxtBtn);
        cancelOrder = findViewById(R.id.cancelOrderBtn);


        confirmOrderBtnShopper = (Button) findViewById(R.id.orderDoneBtn);
        // Add_button add click listener
        viewOrderBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ViewOrderInfoShopper.class);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        viewShoppingListBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ShoppingListShopperView.class);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        uploadRcptBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, UploadReceipt.class);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        confirmOrderBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ShopperRatesBuyer.class);
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
                                    if (snapshot.child("shopperId").getValue(String.class) != null) {
                                        if (snapshot.child("shopperId").getValue(String.class).equals(userID)) {
                                            String orderNum = snapshot.getKey();
                                            newOrder order;
                                            order = snapshot.getValue(newOrder.class);
                                            order.shopperId = "";
                                            buyerId = order.buyerId;
                                            storeName = order.storeName;
                                            address = order.address;
                                            orderId = order.orderId;
                                            shoppingList = order.shoppingList;
                                            shopperId = order.shopperId;
                                            receiptCopy = order.receiptCopy;
                                            paymentType = order.paymentType;
                                            otherInfo = order.otherInfo;
                                            databaseOrders.child(orderNum).setValue(order);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                Intent intent = new Intent(OrderFulfillShopper.this, CurrentTripsShopper.class);
                startActivity(intent);
            }
        });
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, CurrentTripsShopper.class);
        startActivity(intent);
    }
}