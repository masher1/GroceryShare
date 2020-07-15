package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class OrderActivity extends AppCompatActivity {
    private CardView addlist;
    private CardView personal;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");

        addlist = findViewById(R.id.addlistId);
        addlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlist();

            }
        });

        personal = findViewById(R.id.personalid);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpersonal();

            }
        });
    }

    public void openlist(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void openpersonal(){
        Intent intent = new Intent(this, PersonalActivity.class);
        intent.putExtra("USER_ID", userID);
        startActivity(intent);
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }
}