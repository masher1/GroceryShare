package com.example.groceryshare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShopperSignup2 extends AppCompatActivity implements AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener {
    //Write to file initialization

    String profilePhoto, username, email, password, firstName, lastName, address, birthday, phoneNumber, frequency;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText addressInput;
    private EditText birthdayInput;
    private EditText phoneNumberInput;
    private Spinner frequencyspinner;

    Button joinButton;
    Button logInButton;

    DatabaseReference databaseShoppers;
    //Screen toggle initialization

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_signup2);

        Intent intent = getIntent();

        profilePhoto = intent.getStringExtra("PROFILE_PHOTO");
        username = intent.getStringExtra("USER_NAME");
        email = intent.getStringExtra("EMAIL");
        password = intent.getStringExtra("PASSWORD");

        Button datePickerButton = (Button) findViewById(R.id.datePicker);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        databaseShoppers = FirebaseDatabase.getInstance().getReference("Shoppers");

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        /* use findViewById() to get the EditTexts */
        firstNameInput = (EditText)findViewById(R.id.FirstNameInput);
        lastNameInput = (EditText)findViewById(R.id.LastNameInput);
        addressInput = (EditText) findViewById(R.id.AddressInput);
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
        logInButton = (Button) findViewById(R.id.LogInbtn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogIn();
            }
        });

        joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShopperCredentials();
                goHomeScreen();
            }
        });
    }

    private void addShopperCredentials(){
        firstName = firstNameInput.getText().toString();
        lastName = lastNameInput.getText().toString();
        address = addressInput.getText().toString();
        birthday = birthdayInput.getText().toString();
        phoneNumber = phoneNumberInput.getText().toString();
        frequency = String.valueOf(frequencyspinner.getSelectedItem());

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(birthday) && !TextUtils.isEmpty(phoneNumber)){
            String id = databaseShoppers.push().getKey();
            newShopperCreds shopper = new newShopperCreds(id, profilePhoto, username, email, password, firstName, lastName, address, phoneNumber, birthday, frequency);
            databaseShoppers.child(id).setValue(shopper);

            Toast.makeText( this,  "New Shopper Added! ", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ShopperHomeScreen.class);
            startActivity(intent);
        }
        else{
            Toast.makeText( this,  "Please fill all of the fields!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
        birthdayInput = findViewById(R.id.BirthdayInput);
        birthdayInput.setText(currentDateString);
        birthdayInput.setGravity(Gravity.CENTER_HORIZONTAL);
        birthdayInput.setGravity(Gravity.CENTER_VERTICAL);
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperSignup.class);
        startActivity(intent);
    }

    //used to navigate back to the Login Screen
    public void goLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goHomeScreen() {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void addListenerOnSpinnerItemSelection() {
        frequencyspinner = (Spinner) findViewById(R.id.FrequencyInput);
        frequencyspinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
        frequencyspinner = (Spinner) findViewById(R.id.FrequencyInput);
        joinButton = (Button) findViewById(R.id.joinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopperSignup2.this,
                        "OnClickListener : " +
                                "\nSpinner: "+ String.valueOf(frequencyspinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
