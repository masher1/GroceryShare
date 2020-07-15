package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalActivity extends AppCompatActivity {
    String profilePic, name, address, store, payment, rewards, others, userId;
    EditText storeInput;
    EditText paymentInput;
    EditText rewardsInput;
    EditText othersInput;
    EditText addressInput;
    Button submitButton;

    DatabaseReference databaseBuyers;
//    TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity);

        Intent intent = getIntent();

        databaseBuyers = FirebaseDatabase.getInstance().getReference("Buyers");
        DatabaseReference buyerAddress = databaseBuyers.child("-MCDy7cXDPYgWvLh7SVV").child("address");

        buyerAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                address = dataSnapshot.getValue().toString();
                addressInput.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        buyerAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                address = dataSnapshot.getValue().toString();
                addressInput.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        addressInput = (EditText) findViewById(R.id.addressid);
        storeInput = (EditText) findViewById(R.id.storeprefId);
        paymentInput = (EditText) findViewById(R.id.paymentId);
        rewardsInput = (EditText) findViewById(R.id.rewardsId);
        othersInput = (EditText) findViewById(R.id.othersid);

        submitButton = (Button) findViewById(R.id.joinButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBuyerPreferences();
            }
        });

    }


    private void addBuyerPreferences() {
        store = storeInput.getText().toString();
        payment = paymentInput.getText().toString();
        rewards = rewardsInput.getText().toString();
        others = othersInput.getText().toString();

       // String id = databaseBuyers.push().getKey();
        buyerPref buyer = new buyerPref(userId, store, payment, rewards, others);
      //  databaseBuyers.child(id).setValue(buyer);

        Toast.makeText(this, "Submitted Info!", Toast.LENGTH_LONG).show();
    }



    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}