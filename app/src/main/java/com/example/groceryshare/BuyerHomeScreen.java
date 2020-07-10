package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.groceryshare.ui.login.LoginActivity;

public class BuyerHomeScreen extends AppCompatActivity {
    private CardView neworder;
    private CardView pendingorder;
    private CardView pastorder;
    private CardView settings;
    private CardView rate;
    private Button   logOutBuyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyerhomescreen);


        neworder = findViewById(R.id.neworderId);
        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openorderscreen();

            }
        });

        pendingorder = findViewById(R.id.pendingorderId);
        pendingorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpending();

            }
        });

        pastorder = findViewById(R.id.pastorderId);
        pastorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpast();

            }
        });

        settings = findViewById(R.id.settingsId);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensettings();

            }
        });

        rate = findViewById(R.id.ratingId);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openrating();

            }
        });

        logOutBuyer = findViewById(R.id.logOutBtnBuyer);
        logOutBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();

            }
        });
    }


    public void openorderscreen(){
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void openpending(){
        Intent intent = new Intent(this, PendingActivity.class);
        startActivity(intent);
    }

    public void openpast(){
        Intent intent = new Intent(this, PastActivity.class);
        startActivity(intent);
    }

    public void opensettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openrating(){
        Intent intent = new Intent(this, RatingActivity.class);
        startActivity(intent);
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}