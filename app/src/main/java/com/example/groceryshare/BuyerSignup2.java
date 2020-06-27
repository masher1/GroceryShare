package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;

public class BuyerSignup2 extends AppCompatActivity {
    //  TextField Data Collection Start
    String name, address, birthday, disabilities;
    EditText nameInput;
    EditText addressInput;
    EditText birthdayInput;
    EditText disabilitiesInput;

    Button joinButton;
    //    TextField Data Collection End

//    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_signup2);

//        img = findViewById(R.id.GoBackIcon);//defines the back button image

        nameInput = (EditText) findViewById(R.id.FullNameInput);
        addressInput = (EditText) findViewById(R.id.AddressInput);
        birthdayInput = (EditText) findViewById(R.id.BirthdayInput);
        disabilitiesInput = (EditText) findViewById(R.id.DisabilitiesInput);


        joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                address = addressInput.getText().toString();
                birthday = birthdayInput.getText().toString();
                disabilities = disabilitiesInput.getText().toString();

                System.out.println("Full Name: " + name);
                System.out.println("Address: " + address);
                System.out.println("Birthday: " + birthday);
                System.out.println("Disabilities: " + disabilities);
            }
        });
    }

    //used to navigate back to the previous screen
//    public void goBack() {
//        Intent intent = new Intent(this, BuyerSignup.class);
//        startActivity(intent);
//    }
}