package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button loginbutton;
    private Button signupbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        loginbutton = (Button) findViewById(R.id.button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();

            }
        });

        signupbutton = (Button) findViewById(R.id.button2);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignup();

            }
        });

    }

    public void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openSignup(){
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
    }
}


