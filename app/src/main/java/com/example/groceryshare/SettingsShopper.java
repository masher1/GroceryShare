package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.groceryshare.ui.login.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsShopper extends AppCompatActivity {
    private Button personalBtn;
    private Button privacyBtn;
    private Button logoutBtn;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    String userID;
    private String emailAddress;
    private Button passwordReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_shopper);
        mAuth = FirebaseAuth.getInstance();
        passwordReset = findViewById(R.id.changePasswordShopper);
        passwordReset.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        userID = user.getUid();
                        emailAddress = user.getEmail();
                        FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        }
                                    }
                                });

                    }


                }
        );
        personalBtn = findViewById(R.id.personalinfoshopper);
        personalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPersonal();

            }
        });


        logoutBtn = findViewById(R.id.logoutshopperbtn);
        logoutBtn.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        mAuth.signOut();
                        //add way to handle empty or bad input
                        Intent intent = new Intent(SettingsShopper.this, MainActivity.class);

                        // start the activity connect to the specified class
                        startActivity(intent);

                    }


                }
        );

    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        finish();
    }

    public void openPersonal() {
        Intent intent = new Intent(this, PersonalActivityShopper.class);
        startActivity(intent);
    }
}