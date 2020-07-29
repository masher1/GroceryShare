package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderFulfillShopper extends AppCompatActivity {
    private Button viewOrderBtnShopper;
    private Button viewShoppingListBtnShopper;
    private Button uploadRcptBtnShopper;
    private Button confirmOrderBtnShopper;
    private Button cancelOrder;

    DatabaseReference databaseOrders;
    FirebaseUser user;

    private TextView orderNameText;
    public String orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_fulfill_shopper);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            orderid = extras.getString("ORDER_ID");

        /* use findViewById() to get the next Button */
        viewOrderBtnShopper = (Button) findViewById(R.id.orderInfoBtn);
        viewShoppingListBtnShopper = (Button) findViewById(R.id.viewListBtnShopper);
        uploadRcptBtnShopper = (Button) findViewById(R.id.uploadRcptTxtBtn);
        cancelOrder = findViewById(R.id.cancelOrderBtn);

        orderNameText = (TextView) findViewById(R.id.orderNameTxt);

        confirmOrderBtnShopper = (Button) findViewById(R.id.orderDoneBtn);


        orderNameText.setText("Order Number: " + orderid);
        // Add_button add click listener
        viewOrderBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ViewOrderInfoShopper.class);
                intent.putExtra("orderid", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        viewShoppingListBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ShoppingListShopperView.class);
                intent.putExtra("orderid",orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        uploadRcptBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, UploadReceipt.class);
                intent.putExtra("Order_ID", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        confirmOrderBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ShopperRatesBuyer.class);
                intent.putExtra("ORDER_ID", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseOrders.child(orderid).child("shopperId").setValue(null);
                databaseOrders.child(orderid).child("Status").setValue("Available");
                finish();
            }
        });
    }

    //used to navigate back to the screen it came from
    public void goBack(View v) {
        finish();
    }
}