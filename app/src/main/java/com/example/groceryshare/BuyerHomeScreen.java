package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class BuyerHomeScreen extends AppCompatActivity {
    private CardView newOrder;
    private CardView pendingOrder;
    private CardView pastOrder;
    private CardView settings;
    private CardView rate;
    private CardView complaint;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_homescreen);

        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");
        newOrder = findViewById(R.id.neworderId);
        pendingOrder = findViewById(R.id.pendingorderId);
        pastOrder = findViewById(R.id.pastorderId);
        settings = findViewById(R.id.settingsId);
        rate = findViewById(R.id.ratingIDBuyer);
        complaint = findViewById(R.id.problemIDBuyer);

        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerHomeScreen.this, OrderActivity.class);
                intent.putExtra("USER_ID", userID);
                startActivity(intent);

            }
        });


        pendingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerHomeScreen.this, PendingActivity.class);
                startActivity(intent);

            }
        });


        pastOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerHomeScreen.this, PastActivity.class);
                startActivity(intent);

            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerHomeScreen.this, SettingsActivity.class);
                startActivity(intent);

            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerHomeScreen.this, BuyerRatesShopper.class);
                startActivity(intent);

            }
        });
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerHomeScreen.this, buyerComplaints.class);
                startActivity(intent);

            }
        });
    }


}