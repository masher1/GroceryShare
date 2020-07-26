package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewReceipt extends AppCompatActivity {
    //replace with receipt images
    private Integer images[] = {R.drawable.ic_baseline_add_a_photo_24, R.drawable.joinbtn, R.drawable.ic_arrow_back_darkgreen};
    TextView totalView;
    String userID;
    String total;
    String orderID;
    Long numOfPicsLong;
    int numOfPicsInt;
    int numForLoop;
    String referencePic;
    String receipt;
    String imgArray [];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_receipt);
        /*Intent intent = getIntent();
        userID = intent.getStringExtra("USER_ID");
        numForLoop=0;
        FirebaseDatabase.getInstance().getReference().child("Orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.child("shopperId").getValue(String.class) != null) {
                                if (snapshot.child("shopperId").getValue(String.class).equals(userID)) {
                                    numOfPicsLong = snapshot.getChildrenCount() - 9;
                                    numOfPicsInt = numOfPicsLong.intValue();
                                    imgArray = new String [numOfPicsInt];
                                    for (int i = 0; i<numOfPicsInt; i++){
                                         referencePic = "Picture Number " + numForLoop + " Reference String";
                                         System.out.println("Reference Pic" + referencePic);
                                         receipt = snapshot.child(referencePic).getValue(String.class);
                                         imgArray[0] = receipt;
                                         numForLoop++;
                                    }
                                    orderID = snapshot.child("orderId").getValue(String.class);
                                    total = snapshot.child("Total").getValue(String.class);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        totalView = findViewById(R.id.totalDue);
        if (total !=null) {
            totalView.setText(total);
        }
        else {
            System.out.println("Total is Null!!!");
        }

        addImagesToThegallery(); */

    }

   /* private void addImagesToThegallery() {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.imageGallery);
        for (Integer image : images) {
            imageGallery.addView(getImageView(image));
        }
    }


    private View getImageView(Integer image) {
        ImageView imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 10, 0);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(image);
        return imageView;
    }
    */
    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderFulfillShopper.class);
        startActivity(intent);
    }
}