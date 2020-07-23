package com.example.groceryshare;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonalActivity extends AppCompatActivity {

    String[] name;
    String firstName;
    String lastName;
    String address;
    String store;
    String payment;
    String others;
    String userID;

    EditText storeInput;
    EditText paymentInput;
    EditText othersInput;
    EditText nameInput;

    EditText addressInput;
    Button submitButton;


    //Profile Pic Content Start
    String profileImage;
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    private int REQUEST_CAMERA = 0;
    private String userChoosenTask;
    Bitmap imageBitmap;
    private StorageReference StorageRef;
    private static final String TAG = "PersonalActivity";
    //Profile Pic Content End

    DatabaseReference databaseBuyers;
    DatabaseReference databaseOrders;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    TextField Data Collection End

    ImageView img; //used for the back button navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity);

        databaseOrders = FirebaseDatabase.getInstance().getReference("Personal Info");
        databaseBuyers = FirebaseDatabase.getInstance().getReference("Buyers");


        img = findViewById(R.id.GoBackIcon);//defines the back button image
        addressInput = (EditText) findViewById(R.id.addressid);
        nameInput = (EditText) findViewById(R.id.nameid);
        ProfileImage = (ImageView) findViewById(R.id.imageid);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Log.d(TAG, "onCreate: " + user.getDisplayName());
            if (user.getDisplayName() != null) {
                addressInput.setText(user.getUid());
                nameInput.setText(user.getDisplayName());
                nameInput.setSelection(user.getDisplayName().length());
            }
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(ProfileImage);
            }
        }

        //TODO: There is an error message that shows up when you access this activity twice from the main screen
        FirebaseDatabase.getInstance().getReference().child("Buyers").child(user.getUid())
          .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        newBuyerCreds buyer = dataSnapshot.getValue(newBuyerCreds.class);
                        addressInput.setText(buyer.getAddress());
                        nameInput.setText(buyer.getFirstName() + " " + buyer.getLastName());
                        storeInput.setText(buyer.getStore());
                        paymentInput.setText(buyer.getPayment());
                        othersInput.setText(buyer.getOthers());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        img = findViewById(R.id.GoBackIcon);//defines the back button image

        addressInput = (EditText) findViewById(R.id.addressid);
        storeInput = (EditText) findViewById(R.id.storeprefId);
        paymentInput = (EditText) findViewById(R.id.paymentId);
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
        name = nameInput.getText().toString().split(" ", 2);
        firstName = name[0];
        lastName = name[1];
        address = addressInput.getText().toString();
        store = storeInput.getText().toString();
        payment = paymentInput.getText().toString();
        others = othersInput.getText().toString();

        try{
            handleUpload(imageBitmap, user);
        }
        catch (NullPointerException e){
            Log.e(TAG, "Error: ", e.getCause());
        }

        newBuyerCreds buyer = new newBuyerCreds(user.getUid(), firstName, lastName, address, store, payment, others);
        databaseBuyers.child(user.getUid()).setValue(buyer);


        Toast.makeText(this, "Submitted Info!", Toast.LENGTH_LONG).show();
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProfileImage.setImageBitmap(bm);
        imageBitmap = bm;
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ProfileImage.setImageBitmap(bm);
        imageBitmap = bm;

    }

    public void galleryIntent() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void handleImageClick(View view) {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkPermission(PersonalActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void handleUpload(Bitmap bitmap, final FirebaseUser user) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(user.getUid() + ".jpeg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        new Thread(new Runnable() {
                            public void run() {
                                getDownloadUrl(reference, user);
                            }
                        }).start();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e.getCause());
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference, final FirebaseUser user) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);
                        profileImage = uri.toString();
                        setUserProfileUrl(uri, user);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri, FirebaseUser user) {
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