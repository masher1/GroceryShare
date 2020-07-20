package com.example.groceryshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class PersonalActivity extends AppCompatActivity {
    String profilePic;
    String name;
    String address;
    String store;
    String payment;
    String rewards;
    String others;
    String userID;

    private static final String TAG = "PersonalActivity";
    int TAKE_IMAGE_CODE = 10001;
    String DISPLAY_NAME = null;
    String PROFILE_IMAGE_URL = null;

    EditText storeInput;
    EditText paymentInput;
    EditText rewardsInput;
    EditText othersInput;
    EditText nameInput;
    ImageView ProfileImage;
    EditText addressInput;
    Button submitButton;

    DatabaseReference databaseBuyers;
//    TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity);

        Intent intent = getIntent();

        userID = intent.getStringExtra("USER_ID");
        //databaseBuyers = FirebaseDatabase.getInstance().getReference("buyers");

        img = findViewById(R.id.GoBackIcon);//defines the back button image
        addressInput = (EditText) findViewById(R.id.addressid);
        nameInput = (EditText) findViewById(R.id.nameid);
        ProfileImage = (ImageView) findViewById(R.id.imageid);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Log.d(TAG, "onCreate: " + user.getDisplayName());
//            if (user.getDisplayName() != null) {
//                addressInput.setText(user.getUid());
//                displayNameEditText.setText(user.getDisplayName());
//                displayNameEditText.setSelection(user.getDisplayName().length());
//            }
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(ProfileImage);
            }
        }

        //TODO: There is an error message that shows up when you access this activity twice from the main screen
        FirebaseDatabase.getInstance().getReference().child("Buyers").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        newBuyerCreds buyer = dataSnapshot.getValue(newBuyerCreds.class);
                        addressInput.setText(buyer.getAddress());
                        nameInput.setText(buyer.getFirstName() + " " + buyer.getLastName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        addressInput = (EditText) findViewById(R.id.addressid);
        storeInput = (EditText) findViewById(R.id.storeprefId);
        paymentInput = (EditText) findViewById(R.id.paymentId);
        rewardsInput = (EditText) findViewById(R.id.rewardsId);
        othersInput = (EditText) findViewById(R.id.othersid);

        submitButton = (Button) findViewById(R.id.joinButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBuyerPreferences();
            }
        });
    }


    private void addBuyerPreferences() {
        store = storeInput.getText().toString();
        payment = paymentInput.getText().toString();
        rewards = rewardsInput.getText().toString();
        others = othersInput.getText().toString();

        // String id = databaseBuyers.push().getKey();
        //buyerPref buyer = new buyerPref(userId, store, payment, rewards, others);
        //  databaseBuyers.child(id).setValue(buyer);

        Toast.makeText(this, "Submitted Info!", Toast.LENGTH_LONG).show();
    }


    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void updateProfile(final View view) {
        view.setEnabled(false);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(DISPLAY_NAME)
                .build();

        firebaseUser.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        view.setEnabled(true);
                        Toast.makeText(PersonalActivity.this, "Successfully updated profile", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.setEnabled(true);
                        Log.e(TAG, "onFailure: ", e.getCause());
                    }
                });
    }

    public void handleImageClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ProfileImage.setImageBitmap(bitmap);
                    handleUpload(bitmap);
            }
        }
    }

    private void handleUpload(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e.getCause());
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PersonalActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PersonalActivity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}