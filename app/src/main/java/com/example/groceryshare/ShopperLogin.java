package com.example.groceryshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;



public class ShopperLogin extends AppCompatActivity {
    //Write to file initialization
    //profile pic
    private ImageView profilePic;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    private EditText firstNameShop;
    private EditText lastNameShop;
    private EditText addressShop;
    private EditText emailShop;
    private EditText passwordShop;


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
        setContentView(R.layout.activity_shopper_login);
        /* use findViewById() to get the EditTexts */
        firstNameShop = (EditText)findViewById(R.id.firstNameS);
        lastNameShop = (EditText)findViewById(R.id.lastNameS);
        addressShop = (EditText)findViewById(R.id.addressShopperSetUp);
        emailShop = (EditText)findViewById(R.id.emailShopperSetUp);
        passwordShop = (EditText)findViewById(R.id.PasswordShopperSetUp);

        profilePic = findViewById(R.id.profilePicImg);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });
        /* use findViewById() to get the next Button */
        next_activity_button = (Button) findViewById(R.id.shopperSetUpDone);
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
                Intent intent = new Intent(ShopperLogin.this, ShopperHomeScreen.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
