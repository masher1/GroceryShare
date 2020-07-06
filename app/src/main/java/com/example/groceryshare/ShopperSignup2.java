package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShopperSignup2 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //Write to file initialization

    String username, email, password, firstName, lastName, address, birthday, phoneNumber, frequency;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText addressInput;
    private EditText birthdayInput;
    private EditText phoneNumberInput;
    private Spinner spinner;

    DatabaseReference databaseShoppers;
    //Screen toggle initialization
    Button joinButton;

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_signup2);

        Intent intent = getIntent();

        username = intent.getStringExtra("USER_NAME");
        email = intent.getStringExtra("EMAIL");
        password = intent.getStringExtra("PASSWORD");

        databaseShoppers = FirebaseDatabase.getInstance().getReference("shoppers");

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        /* use findViewById() to get the EditTexts */
        firstNameInput = (EditText)findViewById(R.id.FirstNameInput);
        lastNameInput = (EditText)findViewById(R.id.LastNameInput);
        addressInput = (EditText) findViewById(R.id.AddressInput);
        birthdayInput = (EditText) findViewById(R.id.BirthdayInput);
        phoneNumberInput = (EditText) findViewById(R.id.PhoneInput);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

//        /* use findViewById() to get the next Button */
//        joinButton = (Button) findViewById(R.id.joinButton);
//        // Add_button add click listener
//        joinButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                /*
//                 Intents are objects of the android.content.Intent type. Your code can send them
//                 to the Android system defining the components you are targeting.
//                 Intent to start an activity called SecondActivity with the following code:
//                */
//                firstName = firstNameInput.getText().toString();
//                lastName = lastNameInput.getText().toString();
//                address = addressInput.getText().toString();
//                birthday = birthdayInput.getText().toString();
//                phoneNumber = phoneNumberInput.getText().toString();
//
//                System.out.print("First Name: " + firstName);
//                System.out.println("Last Name: " + lastName);
//                System.out.println("Address: " + address);
//                System.out.println("Birthday: " + birthday);
//                System.out.println("Phone Number: " + phoneNumber);
//                //TODO: add way to handle empty or bad input
//        });
//    }
        joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShopperCredentials();
            }
        });
    }

    private void addShopperCredentials(){
        firstName = firstNameInput.getText().toString();
        lastName = lastNameInput.getText().toString();
        address = addressInput.getText().toString();
        birthday = birthdayInput.getText().toString();
        phoneNumber = phoneNumberInput.getText().toString();
        frequency = String.valueOf(spinner.getSelectedItem());

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(birthday)){
            String id = databaseShoppers.push().getKey();
            newShopperCreds buyer = new newShopperCreds(id, username, email, password, firstName, lastName, address, phoneNumber, birthday, frequency);
            databaseShoppers.child(id).setValue(buyer);

            Toast.makeText( this,  "New Shopper Added! ", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText( this,  "Please fill all of the fields!", Toast.LENGTH_LONG).show();
        }
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperSignup.class);
        startActivity(intent);
    }

    //used to navigate back to the Login Screen
    public void goLogIn(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.FrequencyInput);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
        spinner = (Spinner) findViewById(R.id.FrequencyInput);
        joinButton = (Button) findViewById(R.id.joinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopperSignup2.this,
                        "OnClickListener : " +
                                "\nSpinner: "+ String.valueOf(spinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
