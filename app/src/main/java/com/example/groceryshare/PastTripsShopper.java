package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PastTripsShopper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_trips_shopper);
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }
}