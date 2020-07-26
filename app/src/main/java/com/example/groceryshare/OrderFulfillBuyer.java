package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderFulfillBuyer extends AppCompatActivity {
    private Button viewShopInfo;
    private Button viewShopList;
    private Button viewReceipt;
    private Button confirmOrderDone;
    private TextView orderNameText;
    public String orderid;
    public String shopperid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_fulfill_buyer);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            orderid = extras.getString("orderid");
            shopperid = extras.getString("shopperid");

        viewShopInfo = findViewById(R.id.viewShopperInfoBtn);
        viewShopList = findViewById(R.id.viewShoppingListBtn);
        viewReceipt = findViewById(R.id.viewReceiptBtn);
        confirmOrderDone = findViewById(R.id.confirmOrderDoneBtn);

        orderNameText = (TextView) findViewById(R.id.textView3);

        orderNameText.setText("Order Number: " + orderid);

        viewShopInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillBuyer.this, shopperInfoCurrentOrder.class);
                intent.putExtra("orderid", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });

        viewShopList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillBuyer.this, viewShoppingListCurrentOrder.class);
                intent.putExtra("orderid", orderid);
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
    }
    public void goBack(View v) {
        Intent intent = new Intent(this, PendingActivity.class);
        startActivity(intent);
    }
}