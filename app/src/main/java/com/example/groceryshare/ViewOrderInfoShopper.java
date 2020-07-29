package com.example.groceryshare;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class ViewOrderInfoShopper extends AppCompatActivity {

    String name;
    String firstName;
    String lastName;
    String address;
    String store;
    String payment;
    String others;
    String userID;
    String shopperId;
    String buyerId;
    double rating;

    TextView addressInput;
    TextView nameInput;
    TextView paymentInput;
    TextView othersInput;
    TextView storeInput;
    TextView ratingsInput;


    //Profile Pic Content Start
    String profileImage;
    String orderid;
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    private int REQUEST_CAMERA = 0;
    private String userChoosenTask;
    Bitmap imageBitmap;
    private StorageReference StorageRef;
    private static final String TAG = "ViewOrderInfoShopper";
    //Profile Pic Content End

    DatabaseReference databaseBuyers;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_info_shopper);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            orderid = extras.getString("orderid");

        databaseBuyers = FirebaseDatabase.getInstance().getReference("Shoppers");


        img = findViewById(R.id.GoBackIcon);//defines the back button image
        addressInput = (TextView) findViewById(R.id.addressid);
        nameInput = (TextView) findViewById(R.id.nameid);
        storeInput = (TextView) findViewById(R.id.storeprefId);
        paymentInput = (TextView) findViewById(R.id.paymentId);
        othersInput = (TextView) findViewById(R.id.othersid);
        ProfileImage = (ImageView) findViewById(R.id.imageid);
        ratingsInput = (TextView) findViewById(R.id.ratingid);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                buyerId = dataSnapshot.child("Orders").child(orderid).child("buyerId").getValue(String.class);
                getBuyer(database, dataSnapshot, buyerId);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void getBuyer(DatabaseReference database, DataSnapshot dataSnapshot, String buyerId) {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Buyers");
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("buyerID").getValue(String.class).equals(buyerId)) {
                address = snapshot.child("address").getValue(String.class);
                name = snapshot.child("firstName").getValue(String.class) + " " + snapshot.child("lastName").getValue(String.class);
                store = snapshot.child("store").getValue(String.class);
                payment = snapshot.child("payment").getValue(String.class);
                others = snapshot.child("others").getValue(String.class);
                if(snapshot.child("Rating").getValue(double.class) == null){
                    rating = 10.00;
                }
                else{
                    rating = snapshot.child("Rating").getValue(double.class);
                }
                viewText(address, name, store, payment, others, rating);
            }
        }
    }



    //used to navigate back to the previous screen
        public void goBack(View v){
            finish();
        }

        public void viewText(String address,String name, String store, String payment, String others, double rating){
            String result = new DecimalFormat("#.##").format(rating);
            addressInput.setText(address);
            nameInput.setText(name);
            storeInput.setText(store);
            paymentInput.setText(payment);
            othersInput.setText(others);
            if(result == "10.00"){
                result = "None";
            }
            ratingsInput.setText(result);
        }

    }
