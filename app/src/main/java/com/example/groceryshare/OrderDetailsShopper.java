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

public class OrderDetailsShopper extends AppCompatActivity {
    private Button viewOrderBtnShopper;
    private Button viewShoppingListBtnShopper;
    private Button addOrder;

    DatabaseReference databaseOrders;
    FirebaseUser user;

    private TextView orderNameText;
    public String orderid,userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_shopper);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            orderid = extras.getString("ORDER_ID");
            userID = extras.getString("USER_ID");

        /* use findViewById() to get the next Button */
        viewOrderBtnShopper = (Button) findViewById(R.id.orderInfoBtn);
        viewShoppingListBtnShopper = (Button) findViewById(R.id.viewListBtnShopper);
        addOrder = findViewById(R.id.addOrderBtn);

        orderNameText = (TextView) findViewById(R.id.orderNameTxt);



        orderNameText.setText("Order Number: " + orderid);
        // Add_button add click listener
        viewOrderBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsShopper.this, ViewOrderInfoShopper.class);
                intent.putExtra("orderid", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        viewShoppingListBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsShopper.this, ShoppingListShopperView.class);
                intent.putExtra("orderid",orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        addOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseOrders.child(orderid).child("shopperId").setValue(userID);

                Intent intent = new Intent(OrderDetailsShopper.this, ShopperHomeScreen.class);
                startActivity(intent);
            }
        });
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }
}