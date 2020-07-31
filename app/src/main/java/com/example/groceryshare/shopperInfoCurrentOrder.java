package com.example.groceryshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class shopperInfoCurrentOrder extends AppCompatActivity {

    String name;
    String phone;

    public String orderid;
    public String shopperid;

    TextView nameInput;
    TextView phoneInput;


    //Profile Pic Content Start
    private ImageView ProfileImage;
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
        ProfileImage = (ImageView) findViewById(R.id.imageid);



        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshots = dataSnapshot.child("Orders");
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                    if (snapshot.child("buyerId").getValue(String.class).equals(user.getUid())) {
                        if (snapshot.child("orderId").getValue(String.class).contains(orderid)) {
                            shopperid = snapshot.child("shopperId").getValue(String.class);
                            getShopper(dataSnapshot, shopperid);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getShopper(DataSnapshot dataSnapshot, String shopperId) {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Shoppers");
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("shopperID").getValue(String.class).equals(shopperId)) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages/"+ shopperId + ".jpeg");
                // This gets the download url async
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String downloadUrl = uri.toString();
                        if (!downloadUrl.equals("default")) {
                            Glide.with(shopperInfoCurrentOrder.this).load(downloadUrl).into(ProfileImage);
                        }
                    }
                });
                name = snapshot.child("firstName").getValue(String.class) + " " + snapshot.child("lastName").getValue(String.class) ;
                phone = snapshot.child("phoneNumber").getValue(String.class);
                viewText(name,phone);
            }
        }

    }

    //used to navigate back to the previous screen
    public void goBack(View v){
        finish();
    }

    public void viewText(String name, String phone){
        nameInput.setText(name);
        phoneInput.setText(phone);
    }

}