package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.onesignal.OneSignal;

public class ShopperHomeScreen extends AppCompatActivity {

    private CardView availableTrips;
    private CardView currentTrips;
    private CardView pastTrips;
    private CardView settings;
    private CardView ratings;
    private CardView problems;
    private Button logoutBtn;
    private FirebaseAuth mAuth;
    String userID;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_home_screen);

        mAuth = FirebaseAuth.getInstance();
        // OneSignal Initialization
        OneSignal.startInit(this).init();


        user = FirebaseAuth.getInstance().getCurrentUser();
        OneSignal.sendTag("UserID",userID);

        availableTrips = findViewById(R.id.availableIDShop);
        currentTrips = findViewById(R.id.currentOrderIDShop);
        pastTrips = findViewById(R.id.pastOrderIDShop);
        settings =  findViewById(R.id.settingsIDShop);
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

                intent.putExtra("USER_ID", userID);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        pastTrips.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, PastTripsShopper.class);
                intent.putExtra("USER_ID", userID);
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
        problems.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperHomeScreen.this, ComplaintsShopper.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        logoutBtn.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        mAuth.signOut();
                        //add way to handle empty or bad input
                        Intent intent = new Intent(ShopperHomeScreen.this, MainActivity.class);

                        // start the activity connect to the specified class
                        startActivity(intent);

                    }


                }
        );
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();
    }

}
