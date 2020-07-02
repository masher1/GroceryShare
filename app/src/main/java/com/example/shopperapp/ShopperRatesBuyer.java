package com.example.shopperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class ShopperRatesBuyer extends AppCompatActivity {
    private Button doneBtnRatingByShopper;
    private RatingBar ratingBarShopper;
    private EditText userReviewByShopperEditText;
    Float ratingByShopperValue;
    String userReviewByShopperString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_rates_buyer);
        doneBtnRatingByShopper = (Button)findViewById(R.id.doneRatingByShopperBtn);
        ratingBarShopper = (RatingBar)findViewById(R.id.ratingBarByShopper);
        userReviewByShopperEditText = (EditText)findViewById(R.id.tellUsByShopperInput);
        doneBtnRatingByShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ShopperRatesBuyer.this, ShopperHomeScreen.class);
                // start the activity connect to the specified class
                ratingByShopperValue = ratingBarShopper.getRating();
                userReviewByShopperString=userReviewByShopperEditText.getText().toString();
                System.out.println(ratingByShopperValue);
                System.out.println(userReviewByShopperString);

                startActivity(intent);
            }

        });
    }
}