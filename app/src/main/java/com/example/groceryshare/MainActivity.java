package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groceryshare.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button loginbutton;
    private Button signupbutton;
    private FirebaseAuth mAuth;
    Boolean buyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        mAuth = FirebaseAuth.getInstance();
        loginbutton = findViewById(R.id.button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();

            }
        });

        signupbutton = findViewById(R.id.button2);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignup();

            }
        });

    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            String id = currentUser.getUid();
            //updateUI(id);
        }
    }

    public void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openSignup(){
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
    }
    private void updateUI(final String id){
        buyer = false;
        System.out.println(id + " THis is the id");
        FirebaseDatabase.getInstance().getReference().child("Buyers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String buyerID = snapshot.child("buyerID").getValue(String.class);
                            System.out.println(buyerID + " THis is a buyerID");
                            System.out.println(buyerID.equals(id));
                            if(buyerID.equals(id)){
                                buyer = true;
                            }
                        }
                        if(buyer){
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


