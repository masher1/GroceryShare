package com.example.groceryshare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OrderFulfillShopper extends AppCompatActivity {
    private Button viewOrderBtnShopper;
    private Button viewShoppingListBtnShopper;
    private Button uploadRcptBtnShopper;
    private Button confirmOrderBtnShopper;
    private Button cancelOrder;

    DatabaseReference databaseOrders;
    FirebaseUser user;

    private TextView orderNameText;
    public String orderid;
    public String send_user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_fulfill_shopper);

        mAuth = FirebaseAuth.getInstance();
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            orderid = extras.getString("ORDER_ID");

        /* use findViewById() to get the next Button */
        viewOrderBtnShopper = (Button) findViewById(R.id.orderInfoBtn);
        viewShoppingListBtnShopper = (Button) findViewById(R.id.viewListBtnShopper);
        uploadRcptBtnShopper = (Button) findViewById(R.id.uploadRcptTxtBtn);
        cancelOrder = findViewById(R.id.cancelOrderBtn);

        orderNameText = (TextView) findViewById(R.id.orderNameTxt);

        confirmOrderBtnShopper = (Button) findViewById(R.id.orderDoneBtn);


        orderNameText.setText("Order Number: " + orderid);
        // Add_button add click listener
        viewOrderBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ViewOrderInfoShopper.class);
                intent.putExtra("orderid", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        viewShoppingListBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ShoppingListShopperView.class);
                intent.putExtra("orderid",orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        uploadRcptBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, UploadReceipt.class);
                intent.putExtra("Order_ID", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        confirmOrderBtnShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFulfillShopper.this, ShopperRatesBuyer.class);
                intent.putExtra("ORDER_ID", orderid);
                // start the activity connect to the specified class
                startActivity(intent);
            }

        });
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseOrders.child(orderid).child("shopperId").setValue(null);
                databaseOrders.child(orderid).child("status").setValue("Available");
                sendNotif();
                finish();
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
    }
    //used to navigate back to the screen it came from
    public void goBack(View v) {
        finish();
    }

    public void sendNotifications(final String send_user) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic NDlhM2U2NTktODc4MS00Y2UyLWIzNTAtZTQ3NmViZjQ3MGVh");
                        con.setRequestMethod("POST");

                        //send_user = "iDNEt5xYSJUHD01Lua8jGvNiSMt1";

                        String strJsonBody = "{"
                                + "\"app_id\": \"e8b23997-bfa9-4ffc-8c9d-171c095533c7\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"UserID\", \"relation\": \"=\", \"value\": \"" + send_user + "\"},{\"operator\": \"OR\"},{\"field\": \"amount_spent\", \"relation\": \">\",\"value\": \"0\"}],"
                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"Your order has been cancelled! It is back in the queue.\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

    private void sendNotif() {
        FirebaseDatabase.getInstance().getReference().child("Orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.child("orderId").getValue(String.class).equals(orderid)) {
                                if (snapshot.child("shopperId").getValue(String.class).equals(user.getUid())) {
                                    send_user = snapshot.child("buyerId").getValue(String.class);

                                    sendNotifications(send_user);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }
}