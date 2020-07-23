package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.groceryshare.ui.login.LoginActivity;

public class ShopperHomeScreen extends AppCompatActivity {

    private CardView availableTrips;
    private CardView currentTrips;
    private CardView pastTrips;
    private CardView settings;
    private CardView ratings;
    private CardView problems;
    private Button logoutBtn;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_home_screen);

        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        availableTrips = findViewById(R.id.availableIDShop);
        currentTrips = findViewById(R.id.currentOrderIDShop);
        pastTrips = findViewById(R.id.pastOrderIDShop);
        settings =  findViewById(R.id.settingsIDShop);
        ratings = findViewById(R.id.ratingIDShop);
        problems =  findViewById(R.id.problemIDShop);
        logoutBtn = findViewById(R.id.logOutShopperBtn);
        // Add_button add click listener
        availableTrips.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, ShoppingTripsAvailable.class);

                intent.putExtra("USER_ID", userID);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        currentTrips.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, CurrentTripsShopper.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        pastTrips.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, PastTripsShopper.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        settings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, SettingsShopper.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        ratings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, ReviewsAndRatingsShopper.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        problems.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, ComplaintsShopper.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, LoginActivity.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
    }

}
