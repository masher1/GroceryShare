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

public class ComplaintsShopper extends AppCompatActivity {
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

    private Button submitComplaint;
    ImageView back;
    DatabaseReference databaseComplaints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaints_shopper);

        problemDescrip = (EditText) findViewById(R.id.problemInput);
        orderIssue = (CheckBox) findViewById(R.id.checkBoxComplaintGeneral);
        wrongAddress = (CheckBox) findViewById(R.id.wAddressCheck);
        paymentIssue = (CheckBox) findViewById(R.id.payIssue);
        orderOtherIssue = (EditText) findViewById(R.id.otherOrderCheck);
        appIssue = (CheckBox) findViewById(R.id.appIssueCheck);
        otherIssue = (CheckBox) findViewById(R.id.otherIssueCheck);

        databaseComplaints = FirebaseDatabase.getInstance().getReference("Complaints");
        submitComplaint = (Button) findViewById(R.id.submitComplaintBtn);
        back = findViewById(R.id.GoBackIcon);

        submitComplaint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String id = databaseComplaints.push().getKey();
                databaseComplaints = databaseComplaints.child(id);
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

                Intent intent = new Intent(ComplaintsShopper.this, ShopperHomeScreen.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
         });
    }

    //used to navigate back to the previous screen
    public void goBack(View v) {
        Intent intent = new Intent(this, ShopperHomeScreen.class);
        startActivity(intent);
    }

}