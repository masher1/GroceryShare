package com.example.groceryshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.groceryshare.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    private Button loginbutton;
    private Button signupbutton;
    private Button testBtn;
    private FirebaseAuth mAuth;
    StorageReference storageReference;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        mAuth = FirebaseAuth.getInstance();



    }
    public void onStart() {
        super.onStart();
        storageReference = FirebaseStorage.getInstance().getReference("iconLogo.jpg");

        imageView = findViewById(R.id.imgView);


        Glide.with(this)
                .load(storageReference)
                .into(imageView);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            String id = currentUser.getUid();
            updateUI(id);
        }
        else{
            Intent intent = new Intent(com.example.groceryshare.MainActivity.this, com.example.groceryshare.HomeActivity.class);
            startActivity(intent);
        }
    }

    private void updateUI(final String id){
        FirebaseDatabase.getInstance().getReference().child("Buyers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(id).getValue() != null){
                            Intent intent = new Intent(com.example.groceryshare.MainActivity.this, com.example.groceryshare.BuyerHomeScreen.class);
                            intent.putExtra("USER_ID", id);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(com.example.groceryshare.MainActivity.this, com.example.groceryshare.ShopperHomeScreen.class);
                            intent.putExtra("USER_ID", id);
                            startActivity(intent);
                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }
}


