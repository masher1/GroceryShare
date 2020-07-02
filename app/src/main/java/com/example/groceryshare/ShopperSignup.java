package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

public class ShopperSignup extends AppCompatActivity {
    //Write to file initialization
    EditText firstNameShop;
    EditText lastNameShop;
    EditText addressShop;
    EditText emailShop;
    EditText passwordShop;
    String firstNameTextShop;
    String lastNameTextShop;
    String addressTextShop;
    String emailTextShop;
    String passwordTextShop;
    //Screen toggle initialization
    Button next_activity_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppersignup);
        /* use findViewById() to get the EditTexts */
        firstNameShop = findViewById(R.id.firstNameS);
        lastNameShop = findViewById(R.id.lastNameS);
        addressShop = findViewById(R.id.addressBuyerSetUp);
        emailShop = findViewById(R.id.emailBuyerSetUp);
        passwordShop = findViewById(R.id.PasswordBuyerSetUp);

        /* use findViewById() to get the next Button */
        next_activity_button = findViewById(R.id.buyerSetUpDone);
        // Add_button add click listener
        next_activity_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                /*
                 Intents are objects of the android.content.Intent type. Your code can send them
                 to the Android system defining the components you are targeting.
                 Intent to start an activity called SecondActivity with the following code:
                */
                firstNameTextShop = firstNameShop.getText().toString();
                lastNameTextShop = lastNameShop.getText().toString();
                addressTextShop = addressShop.getText().toString();
                emailTextShop = emailShop.getText().toString();
                passwordTextShop= passwordShop.getText().toString();
                System.out.print("First Name: ");
                System.out.println(firstNameTextShop);
                System.out.println("Last Name: " + lastNameTextShop);
                System.out.println("Address: " + addressTextShop);
                System.out.println("Email: " + emailTextShop);
                System.out.println("Password: " + passwordTextShop);
                //add way to handle empty or bad input
                Intent intent = new Intent(ShopperSignup.this, BuyerHomeScreen.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
    }
}
