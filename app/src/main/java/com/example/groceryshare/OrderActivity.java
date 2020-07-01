package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OrderActivity extends AppCompatActivity {
    private CardView addlist;
    private CardView personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderactivity);


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
        startActivity(intent);
    }
}