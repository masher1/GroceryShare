package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groceryshare.ui.login.LoginActivity;

public class NewAccountActivity extends AppCompatActivity {
    private Button shopperbutton;
    private Button buyerbutton;
    private Button logInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account_activity);

        shopperbutton = findViewById(R.id.shopperbutton);
        shopperbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShopper();

            }
        });

        buyerbutton = findViewById(R.id.buyerbutton);
        buyerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBuyer();

            }
        });

        logInButton = (Button) findViewById(R.id.LogInbtn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogIn();
            }
        });
    }

    public void openShopper(){
        Intent intent = new Intent(this, ShopperSignup.class);
        startActivity(intent);
    }

    public void openBuyer(){
        Intent intent = new Intent(this, BuyerSignup.class);
        startActivity(intent);
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //used to navigate back to the Login Screen
    public void goLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}