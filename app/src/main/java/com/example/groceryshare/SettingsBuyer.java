package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsBuyer extends AppCompatActivity {
    private Button personalBtn;
    private Button privacyBtn;
    private Button logoutBtn;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    String userID;
    private String emailAddress;
    private Button passwordReset;
    private Button policyBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_buyer);
        mAuth = FirebaseAuth.getInstance();
        passwordReset = findViewById(R.id.changePasswordBuyer);
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
        policyBtn = findViewById(R.id.privacypolicy);
        policyBtn.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        //add way to handle empty or bad input
                        Intent intent = new Intent(SettingsBuyer.this, PrivacyPolicy.class);
                        // start the activity connect to the specified class
                        startActivity(intent);

                    }


                }
        );
        logoutBtn = findViewById(R.id.logoutbtn);
        logoutBtn.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        mAuth.signOut();
                        //add way to handle empty or bad input
                        Intent intent = new Intent(SettingsBuyer.this, MainActivity.class);

                        // start the activity connect to the specified class
                        startActivity(intent);

                    }


                }
        );

        personalBtn = findViewById(R.id.personalinfo);
        personalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPersonal();

            }
        });


    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        finish();
    }

    public void openPersonal() {
        Intent intent = new Intent(this, PersonalActivityBuyer.class);
        startActivity(intent);
    }


}