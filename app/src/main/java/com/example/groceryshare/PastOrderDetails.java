package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PastOrderDetails extends AppCompatActivity {
    private Button viewShopInfo;
    private Button viewShopList;
    private Button viewReceipt;
    private Button complain;

    DatabaseReference databaseBuyers;
    DatabaseReference databaseShoppers;
    DatabaseReference databaseOrders;
    FirebaseUser user;
    String userID;
    private TextView orderNameText;
    public String orderid;
    public String shopperid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_order_details);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        databaseBuyers = FirebaseDatabase.getInstance().getReference("Buyers");
        databaseShoppers = FirebaseDatabase.getInstance().getReference("Shoppers");
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            orderid = extras.getString("ORDER_ID");

        viewShopInfo = findViewById(R.id.viewShopperInfoBtn);
        viewShopList = findViewById(R.id.viewShoppingListBtn);
        viewReceipt = findViewById(R.id.viewReceiptBtn);
        complain = findViewById(R.id.complaintBtn);

        orderNameText = (TextView) findViewById(R.id.textView3);

        orderNameText.setText("Order Number: " + orderid);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference receiptRef = rootRef.child("Orders").child(orderid).child("Receipts");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    if (viewReceipt.isEnabled()==false) {
                        viewReceipt.setBackgroundResource(R.drawable.joinbtn);
                        viewReceipt.setEnabled(true);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        receiptRef.addListenerForSingleValueEvent(eventListener);

        viewShopInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PastOrderDetails.this, shopperInfoCurrentOrder.class);
                intent.putExtra("orderid", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });

        viewShopList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PastOrderDetails.this, viewShoppingListCurrentOrder.class);
                intent.putExtra("orderid", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        viewReceipt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PastOrderDetails.this, viewReceipt.class);
                intent.putExtra("orderid", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        complain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PastOrderDetails.this, buyerComplaints.class);
                intent.putExtra("orderid", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });


    }

    public void goBack(View v) {
        finish();
    }
}