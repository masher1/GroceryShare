package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShopperRatesBuyer extends AppCompatActivity {
    private DatabaseReference databaseOrdersShopper;
    private Button doneBtnRatingByShopper;
    private RatingBar ratingBarShopper;
    private EditText userReviewByShopperEditText;
    Float ratingByShopperValue;
    String userReviewByShopperString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_rates_buyer);
        databaseOrdersShopper = FirebaseDatabase.getInstance().getReference("Orders");
        doneBtnRatingByShopper = (Button)findViewById(R.id.doneRatingByShopperBtn);
        ratingBarShopper = (RatingBar)findViewById(R.id.ratingBarByShopper);
        userReviewByShopperEditText = (EditText)findViewById(R.id.tellUsByShopperInput);
        doneBtnRatingByShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ShopperRatesBuyer.this, ShopperHomeScreen.class);
                // start the activity connect to the specified class

                ratingByShopperValue = ratingBarShopper.getRating();
                userReviewByShopperString=userReviewByShopperEditText.getText().toString();
                databaseOrdersShopper.child("Review").setValue(userReviewByShopperString);
                databaseOrdersShopper.child("Rating").setValue(ratingByShopperValue);
                startActivity(intent);
            }

        });
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderFulfillShopper.class);
        startActivity(intent);
    }
}