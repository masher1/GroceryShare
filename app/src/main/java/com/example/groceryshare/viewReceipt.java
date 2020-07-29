package com.example.groceryshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class viewReceipt extends AppCompatActivity {
    //replace with receipt images
    private Integer images[] = {R.drawable.ic_baseline_add_a_photo_24, R.drawable.joinbtn, R.drawable.ic_arrow_back_darkgreen};
    TextView totalView;
    String userID;
    String total;
    String orderID;
    String passedOrderId;
    Long numOfPicsLong;
    int numOfPicsInt;
    int numForLoop;
    String referencePic;
    String receipt;
    String imgArray[];
    FirebaseUser user;
    String firebaseImgString;
    private String receiptImg;
    //ImageView receiptImgView;
    private static final String TAG = "viewReceipt";
    StorageReference storageReference;
    ImageView imageView;
    Button next;
    Button back;
    int nextValue;
    String imgStringToPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_receipt);
        //receiptImgView = findViewById(R.id.receiptImgView);
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            passedOrderId = extras.getString("orderid");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        next = findViewById(R.id.nextReceiptView);
        back = findViewById(R.id.backReceiptView);
        nextValue=0;
        FirebaseDatabase.getInstance().getReference().child("Orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.child("orderId").getValue(String.class) != null) {
                                numForLoop=0;
                                if (snapshot.child("orderId").getValue(String.class).equals(passedOrderId)) {
                                    orderID = snapshot.child("orderId").getValue(String.class);
                                    total = snapshot.child("Receipts/Total").getValue(String.class);
                                    numOfPicsLong = snapshot.child("Receipts").getChildrenCount();
                                    numOfPicsInt = numOfPicsLong.intValue();
                                    imgArray = new String[numOfPicsInt];
                                    for (int i = 0; i < numOfPicsInt-1; i++) {
                                        referencePic = "Picture Number " + numForLoop + " Reference String";
                                        firebaseImgString = orderID + numForLoop + ".jpeg";
                                        receipt = snapshot.child(referencePic).getValue(String.class);
                                        imgArray[i] = firebaseImgString;
                                        numForLoop++;
                                        totalView = findViewById(R.id.totalDue);
                                        if (total != null) {
                                            totalView.setText(total);
                                        } else {
                                            System.out.println("Total is Null!!!");
                                        }
                                        pause(firebaseImgString, imgArray[0]);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
 /*       if (nextValue>=(numOfPicsInt-2)){
            if (next.isEnabled()==true){
                next.setBackgroundResource(R.drawable.joinbtngray);
                next.setEnabled(false);
            }
        }
        if (nextValue<=0){
            if (back.isEnabled()==true){
                back.setBackgroundResource(R.drawable.joinbtngray);
                back.setEnabled(false);
            }
        }*/
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nextValue<(numOfPicsInt-2)) {
                    nextValue++;
                }
                imgStringToPass = imgArray[nextValue];
                pause(firebaseImgString, imgStringToPass);

            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nextValue>0) {
                    nextValue--;
                }
                imgStringToPass = imgArray[nextValue];
                pause(firebaseImgString, imgStringToPass);

            }


        });
        //new code
        // Reference to an image file in Cloud Storage
       // pause(firebaseImgString, imgStringToPass);


    }


    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }
    public void pause(String imgString, String arrayItem) {
        if (imgArray!=null) {
            storageReference = FirebaseStorage.getInstance().getReference("Receipts/" + arrayItem);
            // ImageView in your Activity
            imageView = findViewById(R.id.receiptImgView);

            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            Glide.with(this)
                    .load(storageReference)
                    .into(imageView);
        }
        //end new code

    }
}