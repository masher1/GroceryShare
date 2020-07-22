package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CurrentTripsShopper extends AppCompatActivity {
    private Button temp;
    private Button temp2;
    private Button temp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_trips_shopper);
        temp = findViewById(R.id.tempButton);
        temp2 = findViewById(R.id.tempButtonRates);
        temp3 = findViewById(R.id.tempButtonReviewShopper);
        temp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(CurrentTripsShopper.this, UploadReceipt.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        temp2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(CurrentTripsShopper.this, BuyerRatesShopper.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        temp3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(CurrentTripsShopper.this, ShopperRatesBuyer.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
    }
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }
}