package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryshare.ui.login.LoginActivity;

public class ShoppingTripsAvailable extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[], s3[];
    int images[] = {R.drawable.image, R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_trips_available);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.names);
        s2 = getResources().getStringArray(R.array.store);
        s3 = getResources().getStringArray(R.array.distance);

        Shopping_trips_available_adapter myAdapter = new Shopping_trips_available_adapter(this, s1, s2, s3, images);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}