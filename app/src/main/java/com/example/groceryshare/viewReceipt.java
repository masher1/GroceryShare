package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class viewReceipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_receipt);
    }
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderFulfillBuyer.class);
        startActivity(intent);
    }
}