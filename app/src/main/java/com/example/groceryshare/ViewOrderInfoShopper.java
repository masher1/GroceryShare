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

    String orderid;
    private ImageView ProfileImage;

    DatabaseReference databaseBuyers;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_info_shopper);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null)
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
                getBuyer(dataSnapshot, buyerId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getBuyer(DataSnapshot dataSnapshot, String buyerId) {
        DataSnapshot snapshots;
        snapshots = dataSnapshot.child("Buyers");
        for (DataSnapshot snapshot : snapshots.getChildren()) {
            if (snapshot.child("buyerID").getValue(String.class).equals(buyerId)) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages/"+ buyerId + ".jpeg");
                // This gets the download url async
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String downloadUrl = uri.toString();
                        if (!downloadUrl.equals("default")) {
                            Glide.with(ViewOrderInfoShopper.this).load(downloadUrl).into(ProfileImage);
                        }
                    }
                });
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
            if(rating == 10.0){
                result = "None";
            }
            ratingsInput.setText(result);
        }


}
