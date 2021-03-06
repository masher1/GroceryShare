package com.example.groceryshare;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ShopperRatesBuyer extends AppCompatActivity {
    private DatabaseReference databaseOrders;
    private DatabaseReference databaseBuyers;
    private Button doneBtnRatingByShopper;
    private RatingBar ratingBarShopper;
    private EditText userReviewByShopperEditText;
    Float ratingByShopperValue;
    String userReviewByShopperString;
    String orderID;
    String buyerId;
    String user;
    public String send_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_rates_buyer);

        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        orderID = intent.getStringExtra("ORDER_ID");
        databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
        databaseBuyers = FirebaseDatabase.getInstance().getReference("Buyers");
        doneBtnRatingByShopper = (Button) findViewById(R.id.doneRatingByShopperBtn);
        ratingBarShopper = (RatingBar) findViewById(R.id.ratingBarByShopper);
        userReviewByShopperEditText = (EditText) findViewById(R.id.tellUsByShopperInput);
        doneBtnRatingByShopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ShopperRatesBuyer.this, ShopperHomeScreen.class);
                // start the activity connect to the specified class

                ratingByShopperValue = ratingBarShopper.getRating();

                userReviewByShopperString=userReviewByShopperEditText.getText().toString();
                databaseOrders.child(orderID).child("ReviewByShopper").setValue(userReviewByShopperString);
                databaseOrders.child(orderID).child("RatingByShopper").setValue(ratingByShopperValue);
                databaseOrders.child(orderID).child("status").setValue("Complete");
                System.out.println("Yes");
                FirebaseDatabase.getInstance().getReference()
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                buyerId = dataSnapshot.child("Orders").child(orderID).child("buyerId").getValue(String.class);
                                System.out.println(buyerId);
                                if (dataSnapshot.child("Buyers").child(buyerId).child("Rating").getValue(double.class) == null) {
                                    databaseBuyers.child(buyerId).child("Rating").setValue((double) ratingByShopperValue);
                                    databaseBuyers.child(buyerId).child("numRatings").setValue(1);
                                } else {
                                    int numRatings = dataSnapshot.child("Buyers").child(buyerId).child("numRatings").getValue(int.class) + 1;
                                    double newRating = (double) (dataSnapshot.child("Buyers").child(buyerId).child("Rating").getValue(double.class) * (numRatings - 1) + ratingByShopperValue) / (double) numRatings;
                                    databaseBuyers.child(buyerId).child("numRatings").setValue(numRatings);
                                    databaseBuyers.child(buyerId).child("Rating").setValue(newRating);
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                sendNotif();


                startActivity(intent);
            }

        });
    }


    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, OrderFulfillShopper.class);
        startActivity(intent);
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
                                + "\"contents\": {\"en\": \"Your shopper completed your order!\"}"
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
                            if (snapshot.child("orderId").getValue(String.class).equals(orderID)) {
                                if (snapshot.child("shopperId").getValue(String.class).equals(user)) {
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