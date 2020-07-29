package com.example.groceryshare.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groceryshare.BuyerHomeScreen;
import com.example.groceryshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
Button submit;
String emailAddress;
EditText emailET;
Boolean moveForward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        submit = findViewById(R.id.submitPasswordReset);
        emailET = findViewById(R.id.emailAddressEditText);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                emailAddress = emailET.getText().toString();
                moveForward = checkEmail(emailAddress);
                if (moveForward==true) {
                    startActivity(intent);
                }
            }
        });
    }
    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }
    public Boolean checkEmail(String email){
        Boolean advance;
        if (email!=""){
            advance=true;
            FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            }
                        }
                    });
        }
        else{
            advance=false;
            Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show();
        }
        return advance;
    }
}