package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsBuyer extends AppCompatActivity {
    private Button personalBtn;
    private Button privacyBtn;
    private Button logoutBtn;
    private FirebaseAuth mAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_buyer);
        mAuth = FirebaseAuth.getInstance();

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