package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;

public class BuyerHomeScreen extends AppCompatActivity {
    private CardView newOrder;
    private CardView pendingOrder;
    private CardView pastOrder;
    private CardView settings;
    private CardView rate;
    private CardView complaint;
    Button logoutBtn;
    String userID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_homescreen);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        OneSignal.sendTag("UserID", userId);
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.sendTag("UserID",user.getUid());
        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");
        newOrder = findViewById(R.id.neworderId);
        pendingOrder = findViewById(R.id.pendingorderId);
        pastOrder = findViewById(R.id.pastorderId);
        settings = findViewById(R.id.settingsId);
        rate = findViewById(R.id.ratingIDBuyer);
        complaint = findViewById(R.id.problemIDBuyer);
        logoutBtn = findViewById(R.id.logOutBuyerBtn);

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
                Intent intent = new Intent(BuyerHomeScreen.this, SettingsBuyer.class);
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
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                //add way to handle empty or bad input
                Intent intent = new Intent(BuyerHomeScreen.this, MainActivity.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });
    }


}