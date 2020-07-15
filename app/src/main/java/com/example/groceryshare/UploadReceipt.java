package com.example.groceryshare;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UploadReceipt extends AppCompatActivity {
    //Profile Pic Content Start
    private ImageView ReceiptImg1;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private Button next;
    private Button ButtonUpload1;
    private StorageReference StorageRef;
    private StorageTask UploadTask;
    private android.widget.ProgressBar ProgressBar;
    //Profile Pic Content End
    String receiptImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_receipt);
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderFulfillShopper.class);
        startActivity(intent);
    }

}
        StorageRef = FirebaseStorage.getInstance().getReference("receiptUploads");
        ProgressBar = findViewById(R.id.progress_bar_receipt);
        next = findViewById(R.id.nextButton);
        ButtonUpload1 = findViewById(R.id.uploadBtn1);
        ReceiptImg1 = findViewById(R.id.receipt_img_1);

        ButtonUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UploadTask != null && UploadTask.isInProgress()) {
                    Toast.makeText(UploadReceipt.this, "Upload in progress!", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
                if (next.isEnabled()==false) {
                    next.setBackgroundResource(R.drawable.joinbtn);
                    next.setEnabled(true);
                }
            }
        });

        ReceiptImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
                uploadFile();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //add way to handle empty or bad input
                Intent intent = new Intent(UploadReceipt.this, OrderFulfillShopper.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderFulfillShopper.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            Picasso.with(this).load(imageUri).into(ReceiptImg1);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ReceiptImg1.setImageBitmap(bitmap);
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
            UploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar.setProgress(0);
                        }
                    }, 50);
                    Toast.makeText(UploadReceipt.this, "Upload successful", Toast.LENGTH_LONG).show();
                    receiptImage = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadReceipt.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
