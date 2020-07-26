package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OrderFulfillShopper extends AppCompatActivity {
    private Button viewOrderBtnShopper;
    private Button viewShoppingListBtnShopper;
    private Button uploadRcptBtnShopper;
    private Button confirmOrderBtnShopper;
    String orderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_fulfill_shopper);
        Intent intent = getIntent();
        orderID = intent.getStringExtra("ORDER_ID");

        /* use findViewById() to get the next Button */
        viewOrderBtnShopper = (Button) findViewById(R.id.orderInfoBtn);
        viewShoppingListBtnShopper = (Button) findViewById(R.id.viewListBtnShopper);
        uploadRcptBtnShopper = (Button) findViewById(R.id.uploadRcptTxtBtn);

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
                intent.putExtra("ORDER_ID", orderID);
                // start the activity connect to the specified class
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