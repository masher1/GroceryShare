package com.example.groceryshare;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private EditText total;
    //Profile Pic Content End
    String receiptImageString;
    String userID;
    //add to correct part of database
    String orderReference;
    String orderId;
    DatabaseReference databaseReceipts;
    Integer picNum;
    String totalString;
    String orderNum;
    String orderID;
    String receipt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Intent intent = getIntent();
        //userID = intent.getStringExtra("USER_ID");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_receipt);
        picNum = 0;
        StorageRef = FirebaseStorage.getInstance().getReference("Receipts");
        ProgressBar = findViewById(R.id.progress_bar_receipt);
        next = findViewById(R.id.nextButton);
        ButtonUpload1 = findViewById(R.id.uploadBtn1);
        ReceiptImg1 = findViewById(R.id.receipt_img_1);
        total = findViewById(R.id.totalOwedDecimal);
        Bundle intentOrderID = getIntent().getExtras();
        orderID = intentOrderID.getString("Order_ID");
        final String uploadLocation = "Orders/"+orderID+"/Receipts";
        System.out.println(uploadLocation);
        ButtonUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UploadTask != null && UploadTask.isInProgress()) {
                    Toast.makeText(UploadReceipt.this, "Upload in progress!", Toast.LENGTH_SHORT).show();
                }
                else {
                    receipt = uploadFile();
                    System.out.println("Upload Receipt OrderID" + orderID);
                    databaseReceipts = FirebaseDatabase.getInstance().getReference(uploadLocation);
                    if (receipt!=null){
                        String picLabel = "Picture Number " + (picNum).toString() + " Reference String";
                        databaseReceipts.child(picLabel).setValue(receipt);
                        Toast.makeText(UploadReceipt.this, "Upload successful", Toast.LENGTH_LONG).show();
                        picNum++;
                    }
                    else{
                        Toast.makeText(UploadReceipt.this, "Upload failed, please try again", Toast.LENGTH_LONG).show();
                    }
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
                //databaseReceipts = FirebaseDatabase.getInstance().getReference("Orders");
                databaseReceipts = FirebaseDatabase.getInstance().getReference(uploadLocation);
                totalString = total.getText().toString();
                databaseReceipts.child("Total").setValue(totalString);
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
    private String uploadFile() {
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
                    //Toast.makeText(UploadReceipt.this, "Upload successful", Toast.LENGTH_LONG).show();
                    receiptImageString = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    //replace with orderId path when get it

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
        return receiptImageString;
    }

}

