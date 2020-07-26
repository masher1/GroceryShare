package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopperRatesBuyer extends AppCompatActivity {
    private DatabaseReference databaseOrders;
    private DatabaseReference databaseBuyers;
    private Button doneBtnRatingByShopper;
    private RatingBar ratingBarShopper;
    private EditText userReviewByShopperEditText;
    Float ratingByShopperValue;
    String userReviewByShopperString;
    String orderID;
    String buyerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_rates_buyer);

        Intent intent = getIntent();
        orderID = intent.getStringExtra("ORDER_ID");
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        databaseBuyers = FirebaseDatabase.getInstance().getReference("Buyers");
        doneBtnRatingByShopper = (Button)findViewById(R.id.doneRatingByShopperBtn);
        ratingBarShopper = (RatingBar)findViewById(R.id.ratingBarByShopper);
        userReviewByShopperEditText = (EditText)findViewById(R.id.tellUsByShopperInput);
        doneBtnRatingByShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ShopperRatesBuyer.this, ShopperHomeScreen.class);
                // start the activity connect to the specified class

                ratingByShopperValue = ratingBarShopper.getRating();
                userReviewByShopperString=userReviewByShopperEditText.getText().toString();
                databaseOrders.child(orderID).child("Review").setValue(userReviewByShopperString);
                databaseOrders.child(orderID).child("Rating").setValue(ratingByShopperValue);
                databaseOrders.child(orderID).child("Status").setValue("Complete");
                System.out.println("Yes");
                FirebaseDatabase.getInstance().getReference()
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                buyerId = dataSnapshot.child("Orders").child(orderID).child("buyerId").getValue(String.class);
                                System.out.println(buyerId);
                                if(dataSnapshot.child("Buyers").child(buyerId).child("Rating").getValue(double.class) == null){
                                    databaseBuyers.child(buyerId).child("Rating").setValue((double)ratingByShopperValue);
                                    databaseBuyers.child(buyerId).child("numRatings").setValue(1);
                                }
                                else{
                                    int numRatings = dataSnapshot.child("Buyers").child(buyerId).child("numRatings").getValue(int.class) + 1;
                                    double newRating = (double)(dataSnapshot.child("Buyers").child(buyerId).child("Rating").getValue(double.class) * (numRatings - 1) + ratingByShopperValue)/(double)numRatings;
                                    databaseBuyers.child(buyerId).child("numRatings").setValue(numRatings);
                                    databaseBuyers.child(buyerId).child("Rating").setValue(newRating);
                                }



                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


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