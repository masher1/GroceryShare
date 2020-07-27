package com.example.groceryshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

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

    TextView addressInput;
    TextView nameInput;
    TextView paymentInput;
    TextView othersInput;
    TextView storeInput;


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

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshots = dataSnapshot.child("Orders");
                for (DataSnapshot snapshot : snapshots.getChildren()) {
                    if (snapshot.child("shopperId").getValue(String.class).equals(user.getUid())) {
                        if (snapshot.child("orderId").getValue(String.class).equals(orderid)) {
                            buyerId = snapshot.child("buyerId").getValue(String.class);
                            getbuyer(dataSnapshot, buyerId);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void getbuyer(DataSnapshot dataSnapshot, String buyerId) {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Buyers");
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("buyerID").getValue(String.class).equals(buyerId)) {
                address = snapshot.child("address").getValue(String.class);
                name = snapshot.child("firstName").getValue(String.class);
                store = snapshot.child("store").getValue(String.class);
                payment = snapshot.child("payment").getValue(String.class);
                others = snapshot.child("others").getValue(String.class);
                viewText(address, name, store, payment, others);
            }
        }
    }

    //used to navigate back to the previous screen
        public void goBack(View v){
            Intent intent = new Intent(this, ShopperHomeScreen.class);
            startActivity(intent);
        }

        public void viewText(String address,String name, String store, String payment, String others){
            addressInput.setText(address);
            nameInput.setText(name);
            storeInput.setText(store);
            paymentInput.setText(payment);
            othersInput.setText(others);
        }

    }
