package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BuyerRatesShopper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_rates_shopper);
    }
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }
}