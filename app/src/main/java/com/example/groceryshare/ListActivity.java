package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceryshare.databinding.AdditemBinding;
import com.example.groceryshare.databinding.ListactivityBinding;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String s1[], s2[], s3[];
    TextView addItembtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity);
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

}