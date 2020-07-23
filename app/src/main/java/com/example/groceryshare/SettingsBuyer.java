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

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_buyer);

        logoutBtn = findViewById(R.id.logoutbtn);
        logoutBtn.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                            AuthUI.getInstance()
                                    .signOut(SettingsBuyer.this)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // user is now signed out
                                            startActivity(new Intent(SettingsBuyer.this, LoginActivity.class));
                                            finish();
                                        }
                                    });
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