package com.example.groceryshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    String itemName, quantity, brand;
    private EditText namef;
    private EditText quantityf;
    private EditText brandf;
    //array list for data
    ArrayList<GroceryItem> itemList = new ArrayList<>();
    ItemAdapter itemAdapter;
    RecyclerView recyclerView;
    TextView addbtn;
    Button submitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        namef = (EditText) findViewById(R.id.et_name);
        quantityf = (EditText) findViewById(R.id.et_quantity);
        brandf = (EditText) findViewById(R.id.et_brand);

        recyclerView = findViewById(R.id.recycler_view);
        addbtn = findViewById(R.id.tv_add_item);
        submitbtn = findViewById(R.id.btn_submit);

        itemList = populateList();
        itemAdapter = new ItemAdapter(this,itemList);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,FinalListActivity.class);
                startActivity(intent);
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryItem groceryItem = new GroceryItem();
                itemAdapter.addItem(groceryItem);

            }
        });

    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }


    private ArrayList<GroceryItem> populateList(){

        ArrayList<GroceryItem> list = new ArrayList<>();

        for(int i = 0; i < list.size(); i++){
            GroceryItem groceryItem = new GroceryItem();
            groceryItem.setItemName(String.valueOf(i));
            groceryItem.setQuantity(String.valueOf(i));
            groceryItem.setBrand(String.valueOf(i));
            list.add(groceryItem);
        }

        return list;
    }

   /* private void addShoppingList(){
        for (int i = 0; i < ItemAdapter.shopList.size(); i++) {
            itemName = namef.getText() + " " + ItemAdapter.shopList.get(i).getItemName();
            quantity = quantityf.getText() + " " + ItemAdapter.shopList.get(i).getQuantity();
            brand = brandf.getText() + " " + ItemAdapter.shopList.get(i).getBrand();
        }

        if(!TextUtils.isEmpty(itemName) && !TextUtils.isEmpty(quantity) && !TextUtils.isEmpty(brand)){
            String id = databaseBuyers.push().getKey();
            GroceryItem buyer = new GroceryItem(id, name, quantity, brand);
            databaseBuyers.child(id).setValue(buyer);

            Toast.makeText(getApplicationContext(),  "New Shopping List Added! ", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, BuyerHomeScreen.class);
            startActivity(intent);
        }
    } */
}
