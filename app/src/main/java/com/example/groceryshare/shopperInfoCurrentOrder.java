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

public class shopperInfoCurrentOrder extends AppCompatActivity {

    String name;
    String firstName;
    String lastName;
    String phone;
    String rating;

    public String orderid;
    public String shopperid;

    TextView nameInput;
    TextView phoneInput;
    TextView ratingInput;


    //Profile Pic Content Start
    String profileImage;
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    private int REQUEST_CAMERA = 0;
    private String userChoosenTask;
    Bitmap imageBitmap;
    private StorageReference StorageRef;
    private static final String TAG = "shopperInfoCurrentOrder";
    //Profile Pic Content End

    DatabaseReference databaseShoppers;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_info_current_order);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            orderid = extras.getString("orderid");
            //shopperid = extras.getString("shopperid");

        databaseShoppers = FirebaseDatabase.getInstance().getReference("Shopper");


        img = findViewById(R.id.GoBackIcon);//defines the back button image
        nameInput = (TextView) findViewById(R.id.nameid);
        phoneInput = (TextView) findViewById(R.id.phoneid);
        ratingInput = (TextView) findViewById(R.id.ratingid);
        ProfileImage = (ImageView) findViewById(R.id.imageid);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshots = dataSnapshot.child("Orders");
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                    if (snapshot.child("buyerId").getValue(String.class).equals(user.getUid())) {
                        System.out.println(snapshot.child("buyerId").getValue(String.class) + "  " + user.getUid());
                        if (snapshot.child("orderId").getValue(String.class).contains(orderid)) {
                            shopperid = snapshot.child("shopperId").getValue(String.class);
                            getshopper(database, dataSnapshot, shopperid);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getshopper(DatabaseReference database, DataSnapshot dataSnapshot, String shopperId) {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Shoppers");
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("shopperID").getValue(String.class).equals(shopperId)) {
                name = snapshot.child("firstName").getValue(String.class);
                phone = snapshot.child("phoneNumber").getValue(String.class);
                rating = snapshot.child("lastName").getValue(String.class);
                viewText(name,phone,rating);
            }
        }

    }

      /*  FirebaseDatabase.getInstance().getReference().child("Buyers").child(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        newBuyerCreds buyer = dataSnapshot.getValue(newBuyerCreds.class);
                        nameInput.setText(buyer.getFirstName() + " " + buyer.getLastName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }); */


    //used to navigate back to the previous screen
    public void goBack(View v){
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }

    public void viewText(String name, String phone, String rating){
        nameInput.setText(name);
        phoneInput.setText(phone);
        ratingInput.setText(rating);
    }

}