 /*package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FinalListActivity extends AppCompatActivity {

    private TextView namef;
    private TextView quantityf;
    private TextView brandf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finallistactivity);

        namef = (TextView) findViewById(R.id.et_name2);
        quantityf = (TextView) findViewById(R.id.et_quantity2);
        brandf = (TextView) findViewById(R.id.et_brand2);

        for (int i = 0; i < ItemAdapter.shopList.size(); i++){

            namef.setText(namef.getText() + " " + ItemAdapter.shopList.get(i).getItemName() + System.getProperty("line.separator"));
            quantityf.setText(quantityf.getText() + " " + ItemAdapter.shopList.get(i).getQuantity() + System.getProperty("line.separator"));
            brandf.setText(brandf.getText() + " " + ItemAdapter.shopList.get(i).getBrand() + System.getProperty("line.separator"));

        }


    }
} */