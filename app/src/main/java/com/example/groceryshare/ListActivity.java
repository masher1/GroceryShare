package com.example.groceryshare;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Date;

public class ListActivity extends AppCompatActivity {
    //array list for data
    ArrayList<GroceryItem> itemList = new ArrayList<>();
    ItemAdapter itemAdapter;
    RecyclerView recyclerView;
    TextView addbtn;
    Button submitbtn;
    AlertDialog.Builder builder;
    DatabaseReference databaseBuyers;
    DatabaseReference databaseOrders;
    String address;
    Date dateFulfilled;
    Date datePlaced;
    Date dateBy;
    String storeName;
    String payment;
    String receiptcopy;
    String otherInfo;
    String shopperId;
    String buyerId;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        recyclerView = findViewById(R.id.recycler_view);
        addbtn = findViewById(R.id.tv_add_item);
        submitbtn = findViewById(R.id.btn_submit);
        itemList = populateList();
        itemAdapter = new ItemAdapter(this,itemList);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        FirebaseDatabase.getInstance().getReference().child("Buyers").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newBuyerCreds buyer = dataSnapshot.getValue(newBuyerCreds.class);
                address = buyer.getAddress();
                payment = buyer.getPayment();
                storeName = buyer.getStore();
                otherInfo = buyer.getOthers();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        builder = new AlertDialog.Builder(this);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage("Confirm") .setTitle("Confirm your personal information");
                //Setting message manually and performing action on button click
                builder.setMessage("Address: " + address + "\nPayment Type: " + payment + "\nShopping List: " + itemList.toString())
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                addShoppingList();
                                finish();
                                Toast.makeText(getApplicationContext(),"you choose confirm action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"you choose edit action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Confirm Personal Information");
                alert.show();

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
        Intent intent = new Intent(this, OrderActivity.class);
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
    private void addShoppingList(){
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        if(ItemAdapter.shopList.size() != 0){
            String id = databaseOrders.push().getKey();
            NewOrder order = new NewOrder(id, datePlaced, dateFulfilled, dateBy, storeName, user.getUid(), shopperId, receiptcopy, ItemAdapter.shopList, address, payment,otherInfo);
            databaseOrders.child(id).setValue(order);
            Toast.makeText(getApplicationContext(),  "New Shopping List Added!", Toast.LENGTH_LONG).show();
        }
    }
}
