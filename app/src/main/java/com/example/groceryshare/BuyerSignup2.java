package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyerSignup2 extends AppCompatActivity {
    //  TextField Data Collection Start

    String username, email, password, firstName, lastName, address, phoneNumber, birthday, disabilities;
    EditText firstNameInput;
    EditText lastNameInput;
    EditText addressInput;
    EditText phoneNumberInput;
    EditText birthdayInput;
    EditText disabilitiesInput;

    Button joinButton;

    DatabaseReference databaseBuyers;
//    TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_signup2);

        Intent intent = getIntent();

        username = intent.getStringExtra("USER_NAME");
        email = intent.getStringExtra("EMAIL");
        password = intent.getStringExtra("PASSWORD");

        databaseBuyers = FirebaseDatabase.getInstance().getReference("buyers");

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        firstNameInput = (EditText) findViewById(R.id.FirstNameInput);
        lastNameInput = (EditText) findViewById(R.id.LastNameInput);
        addressInput = (EditText) findViewById(R.id.AddressInput);
        birthdayInput = (EditText) findViewById(R.id.BirthdayInput);
        phoneNumberInput = (EditText) findViewById(R.id.PhoneInput);
        disabilitiesInput = (EditText) findViewById(R.id.DisabilitiesInput);


        joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBuyerCredentials();
            }
        });
    }

    private void addBuyerCredentials(){
        firstName = firstNameInput.getText().toString();
        address = addressInput.getText().toString();
        birthday = birthdayInput.getText().toString();
        disabilities = disabilitiesInput.getText().toString();

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(birthday) ){
            String id = databaseBuyers.push().getKey();
            newBuyerCreds buyer = new newBuyerCreds(id, username, email, password, firstName, lastName, address, phoneNumber, birthday);
            databaseBuyers.child(id).setValue(buyer);

            Toast.makeText( this,  "New Buyer Added! ", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText( this,  "Please fill all of the fields!", Toast.LENGTH_LONG).show();
        }
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerSignup.class);
        startActivity(intent);
    }

    //used to navigate back to the Login Screen
    public void goLogIn(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }
}