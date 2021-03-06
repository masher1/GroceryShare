package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingListShopperView extends AppCompatActivity {
    ArrayList<GroceryItem> itemList = new ArrayList<>();
    RecyclerView recyclerView;
    DatabaseReference databaseOrders;
    String orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        orderid = intent.getStringExtra("orderid");

        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_shopper_view);

        recyclerView = findViewById(R.id.recycler_view);

        final listAdapter listAdapter = new listAdapter(this, itemList);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Only shows most recent order info despite which order I click on
        FirebaseDatabase.getInstance().getReference().child("Orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList items = new ArrayList<>();
                        for (DataSnapshot shot : dataSnapshot.child(orderid).child("shoppingList").getChildren()) {
                            String itemName = shot.child("itemName").getValue(String.class);
                            String brand = shot.child("brand").getValue(String.class);
                            String quantity = shot.child("quantity").getValue(String.class);
                            // Using your overloaded class constructor to populate the Order data
                            GroceryItem order = new GroceryItem(itemName, brand, quantity);
                            // here we are adding the order to the ArrayList
                            itemList.add(order);
                        }
                        listAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        finish();
    }

}