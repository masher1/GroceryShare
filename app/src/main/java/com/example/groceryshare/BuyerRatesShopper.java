package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyerRatesShopper extends AppCompatActivity {
    private Button done;
    DatabaseReference databaseOrders;
    private EditText reviewEditText;
    private RatingBar ratingBarValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_rates_shopper);
        done = findViewById(R.id.doneRatingByBuyerBtn);
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        reviewEditText = findViewById(R.id.tellUsByBuyerInput);
        ratingBarValue = findViewById(R.id.ratingBarByBuyer);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerRatesShopper.this, BuyerHomeScreen.class);
                String idReview = databaseOrders.push().getKey();
                String idRating = databaseOrders.push().getKey();
                String review = reviewEditText.getText().toString();
                Float rating = ratingBarValue.getRating();
                databaseOrders.child(idReview).setValue(review);
                databaseOrders.child(idRating).setValue(rating);
                startActivity(intent);

            }
        });
    }
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }



}

