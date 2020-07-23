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
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }

    public void openPersonal(){
        Intent intent = new Intent(this, PersonalActivity.class);
        startActivity(intent);
    }


}