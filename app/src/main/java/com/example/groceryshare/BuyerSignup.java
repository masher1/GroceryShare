package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;

public class BuyerSignup extends AppCompatActivity {

//    Profile Pic Content Start
    private CircleImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
//    Profile Pic Content End

//    TextField Data Collection Start
    String username, email, password;
    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;

    Button nextButton;
//    TextField Data Collection End

    ImageView img; //used for the back button navigation


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyersignup);

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        ProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        usernameInput = (EditText) findViewById(R.id.UsernameInput);
        emailInput = (EditText) findViewById(R.id.EmailInput);
        passwordInput = (EditText) findViewById(R.id.PasswordInput);

        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                System.out.println(username);
                System.out.println(email);
                System.out.println(password);
                gotoNext();
            }
        });
    }

    public void gotoNext(){
        Intent intent = new Intent(this, BuyerSignup2.class);
        intent.putExtra("USER_NAME", username);
        intent.putExtra("EMAIL", email);
        intent.putExtra("PASSWORD", password);
        startActivity(intent);
    }


    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
    }

    //used to navigate back to the Login Screen
    public void goLogIn(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}