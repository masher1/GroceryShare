package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class viewShoppingListCurrentOrder extends AppCompatActivity {
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
        setContentView(R.layout.view_shopping_list_current_order);

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
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String ordernum = snapshot.getKey();
                            if(snapshot.child("orderId").getValue(String.class).equals(orderid)) {
                                if (snapshot.child("buyerId").getValue(String.class).equals(user.getUid())) {
                                    ArrayList items = new ArrayList<>();
                                    for (DataSnapshot shot : snapshot.child("shoppingList").getChildren()) {
                                        String itemName = shot.child("itemName").getValue(String.class);
                                        String brand = shot.child("brand").getValue(String.class);
                                        String quantity = shot.child("quantity").getValue(String.class);
                                        // Using your overloaded class constructor to populate the Order data
                                        GroceryItem order = new GroceryItem(itemName, brand, quantity);
                                        // here we are adding the order to the ArrayList
                                        itemList.add(order);
                                    }
                                }
                            }
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
        Intent intent = new Intent(this, BuyerHomeScreen.class);
        startActivity(intent);
    }

}