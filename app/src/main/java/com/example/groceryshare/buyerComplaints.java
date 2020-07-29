package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class buyerComplaints extends AppCompatActivity {
    private Button submit;
    private Button submitComplaint;
    private EditText problemDescrip;
    private CheckBox orderIssue;
    private CheckBox wrongAddress;
    private CheckBox paymentIssue;
    private EditText orderOtherIssue;
    private CheckBox appIssue;
    private CheckBox otherIssue;

    private String problemDescripString;
    private String orderOtherIssueString;
    private Boolean orderIssueValue;
    private Boolean wrongAddressValue;
    private Boolean paymentIssueValue;
    private Boolean appIssueValue;
    private Boolean otherIssueValue;
    ImageView back;
    DatabaseReference databaseComplaints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_complaints);
        problemDescrip = (EditText) findViewById(R.id.problemInputBuyer);
        orderIssue = (CheckBox) findViewById(R.id.checkBoxComplaintGeneralBuyer);
        wrongAddress = (CheckBox) findViewById(R.id.wAddressCheckBuyer);
        paymentIssue = (CheckBox) findViewById(R.id.payIssueBuyer);
        orderOtherIssue = (EditText) findViewById(R.id.otherOrderCheckBuyer);
        appIssue = (CheckBox) findViewById(R.id.appIssueCheckBuyer);
        otherIssue = (CheckBox) findViewById(R.id.otherIssueCheckBuyer);
        submitComplaint = (Button)findViewById(R.id.submitComplaintBtnBuyer);
        databaseComplaints = FirebaseDatabase.getInstance().getReference("Complaints");

        submitComplaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(buyerComplaints.this, BuyerHomeScreen.class);
                problemDescripString = problemDescrip.getText().toString();
                orderOtherIssueString = orderOtherIssue.getText().toString();
                orderIssueValue = orderIssue.isChecked();
                wrongAddressValue = wrongAddress.isChecked();
                paymentIssueValue = paymentIssue.isChecked();
                appIssueValue = appIssue.isChecked();
                otherIssueValue = otherIssue.isChecked();
                databaseComplaints.child("Problem Description").setValue(problemDescripString);
                databaseComplaints.child("Order issue checkbox").setValue(orderIssueValue);
                databaseComplaints.child("Other option for order issue checkbox").setValue(orderOtherIssueString);
                databaseComplaints.child("Wrong address checkbox").setValue(wrongAddressValue);
                databaseComplaints.child("Payment issue checkbox").setValue(paymentIssueValue);
                databaseComplaints.child("App issue checkbox").setValue(appIssueValue);
                databaseComplaints.child("Other issue checkbox").setValue(otherIssueValue);
                // start the activity connect to the specified class
                startActivity(intent);
            }
        });
    }
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }
}