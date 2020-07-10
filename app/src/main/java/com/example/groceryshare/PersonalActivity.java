package com.example.groceryshare;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class PersonalActivity extends AppCompatActivity {
    String store, payment, rewards, others, userId;
    EditText storeInput;
    EditText paymentInput;
    EditText rewardsInput;
    EditText othersInput;
    TextView address;
    Button submitButton;
    //DatabaseReference databaseBuyers;
//    TextField Data Collection End
    ImageView img; //used for the back button navigation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalactivity);
        Intent intent = getIntent();
        //databaseBuyers = FirebaseDatabase.getInstance().getReference("buyers");
        img = findViewById(R.id.GoBackIcon);//defines the back button image
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
        address = (TextView) findViewById(R.id.addressid);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address.setCursorVisible(true);
                address.setFocusableInTouchMode(true);
                address.setInputType(InputType.TYPE_CLASS_TEXT);
                address.requestFocus();
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