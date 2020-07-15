package com.example.groceryshare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BuyerSignup2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    //  TextField Data Collection Start

    String profilePhoto, username, email, password, firstName, lastName, address, phoneNumber, birthday, disabilities;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText addressInput;
    private EditText phoneNumberInput;
    private TextView birthdayInput;
    private EditText disabilitiesInput;
    private FirebaseAuth mAuth;

    Button joinButton;
    Button logInButton;

    DatabaseReference databaseBuyers;
    //TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_signup2);

        Intent intent = getIntent();

        mAuth = FirebaseAuth.getInstance();
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

        databaseBuyers = FirebaseDatabase.getInstance().getReference("Buyers");

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        firstNameInput = (EditText) findViewById(R.id.FirstNameInput);
        lastNameInput = (EditText) findViewById(R.id.LastNameInput);
        addressInput = (EditText) findViewById(R.id.AddressInput);
        phoneNumberInput = (EditText) findViewById(R.id.PhoneInput);
        disabilitiesInput = (EditText) findViewById(R.id.DisabilitiesInput);

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
                try {
                    addBuyerCredentials();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void addBuyerCredentials() throws IOException, JSONException {
        firstName = firstNameInput.getText().toString();
        lastName = lastNameInput.getText().toString();
        address = addressInput.getText().toString();
        birthday = birthdayInput.getText().toString();
        phoneNumber = phoneNumberInput.getText().toString();
        disabilities = disabilitiesInput.getText().toString();

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(birthday) ){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(BuyerSignup2.this,  "Account Created", Toast.LENGTH_LONG).show();
                                String id = user.getUid();
                                newBuyerCreds buyer = new newBuyerCreds(id, profilePhoto, username, email, firstName, lastName, address, phoneNumber, birthday, disabilities);
                                databaseBuyers.child(id).setValue(buyer);
                                Toast.makeText(BuyerSignup2.this,  "New Buyer Added! ", Toast.LENGTH_LONG).show();
                                updateUI(id);
                            } else {
                                Toast.makeText(BuyerSignup2.this,  "account not created", Toast.LENGTH_LONG).show();
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                //Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }


                        }
                    });

        double [] verification = DistanceCalculator.addressToLonLat(address);
//        new DistanceCalculator(address).execute();
        if (verification == null){
            addressInput.setError("Please Enter A Real Address!");
            address = "";
        }else{
            addressInput.setError(null);
        }
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
        String currentDateString = new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
        birthdayInput = findViewById(R.id.BirthdayInput);
        birthdayInput.setText(currentDateString);
        birthdayInput.setGravity(Gravity.CENTER_HORIZONTAL);
        birthdayInput.setGravity(Gravity.CENTER_VERTICAL);
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerSignup.class);
        startActivity(intent);
    }

    private void updateUI(String id){
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        intent.putExtra("USER_ID", id);
        startActivity(intent);
    }
    //used to navigate back to the Login Screen
    public void goLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}