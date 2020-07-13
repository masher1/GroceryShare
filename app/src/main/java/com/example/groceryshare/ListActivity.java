package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String s1[], s2[], s3[];
    TextView addItembtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        addItembtn = findViewById(R.id.tv_add_item);
        addItembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = findViewById(R.id.recycler_view);

               // s1 = getResources().getStringArray(R.array.names);
              //  s2 = getResources().getStringArray(R.array.store);
               // s3 = getResources().getStringArray(R.array.distance);

                ItemAdapter myAdapter = new ItemAdapter(ListActivity.this,s1,s2,s3);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

            }
        });

    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }

}