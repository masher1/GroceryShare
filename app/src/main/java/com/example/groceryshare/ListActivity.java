package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {
    private ListView mShoppingList;
    private EditText mItemEdit;
    private Button mAddButton;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity);

        mShoppingList = (ListView) findViewById(R.id.shopping_listView);
        mItemEdit = (EditText) findViewById(R.id.item_editText);
        mAddButton = (Button) findViewById(R.id.add_button);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mShoppingList.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = mItemEdit.getText().toString();
                mAdapter.add(item);
                mAdapter.notifyDataSetChanged();
                mItemEdit.setText("");

            }
        });
    }
}