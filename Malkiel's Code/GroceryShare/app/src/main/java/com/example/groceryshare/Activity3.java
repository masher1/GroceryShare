package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity3 extends AppCompatActivity {
    private Button shopperbutton;
    private Button buyerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        shopperbutton = (Button) findViewById(R.id.shopperbutton);
        shopperbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShopper();

            }
        });

        buyerbutton = (Button) findViewById(R.id.buyerbutton);
        buyerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBuyer();

            }
        });

    }

    public void openShopper(){
        Intent intent = new Intent(this,ShopperDashboard.class);
        startActivity(intent);
    }

    public void openBuyer(){
        Intent intent = new Intent(this,BuyerDashboard.class);
        startActivity(intent);
    }

}