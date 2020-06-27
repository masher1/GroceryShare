package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyersignup);

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
//                username = usernameInput.getText().toString();
//                email = emailInput.getText().toString();
//                password = passwordInput.getText().toString();
                gotoNext();
            }
        });
    }

    public void gotoNext(){
        Intent intent = new Intent(this, BuyerSignupContinued.class);
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