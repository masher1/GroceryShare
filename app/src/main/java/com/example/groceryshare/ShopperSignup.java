package com.example.groceryshare;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ShopperSignup extends AppCompatActivity {

    //profile pic
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private Button ButtonUpload;
    private android.widget.ProgressBar ProgressBar;
    private StorageReference StorageRef;
    private StorageTask UploadTask;
    //Profile Pic Content End

    //TextField Data Collection Start
    String profileImage, username, email, password;
    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;

    Button nextButton;
    Button logInButton;

    ImageView img; //used for the back button navigation
    //    TextField Data Collection End

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_signup);

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        StorageRef = FirebaseStorage.getInstance().getReference("profilePicUploads");

        ProfileImage = findViewById(R.id.profile_image);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
                uploadFile();
            }
        });

        usernameInput = (EditText) findViewById(R.id.UsernameInput);
        emailInput = (EditText) findViewById(R.id.EmailInput);
        passwordInput = (EditText) findViewById(R.id.PasswordInput);

        ProgressBar = findViewById(R.id.progress_bar);
        ButtonUpload = findViewById(R.id.button_upload);
        nextButton = (Button) findViewById(R.id.nextButton);

        ButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UploadTask != null && UploadTask.isInProgress()) {
                    Toast.makeText(ShopperSignup.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
                nextButton.setEnabled(true);
            }
        });

        logInButton = (Button) findViewById(R.id.LogInbtn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogIn();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    gotoNext();
                } else
                    Toast.makeText(getApplicationContext(), "Please fill all of the fields!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void gotoNext(){
        Intent intent = new Intent(this, ShopperSignup2.class);
        intent.putExtra("PROFILE_PHOTO", profileImage);
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
    public void goLogIn() {
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

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = StorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            UploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<com.google.firebase.storage.UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar.setProgress(0);
                        }
                    }, 50);
                    Toast.makeText(ShopperSignup.this, "Upload successful", Toast.LENGTH_LONG).show();
                    profileImage = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ShopperSignup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            ProgressBar.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this, "Please Select a Profile Photo!", Toast.LENGTH_SHORT).show();
        }
    }
}
