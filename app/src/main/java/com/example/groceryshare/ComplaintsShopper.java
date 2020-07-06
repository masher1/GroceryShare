package com.example.groceryshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ComplaintsShopper extends AppCompatActivity {
    EditText problemDescrip;
    CheckBox orderIssue;
    CheckBox wrongAddress;
    CheckBox paymentIssue;
    EditText orderOtherIssue;
    CheckBox appIssue;
    CheckBox otherIssue;

    String problemDescripString;
    String orderOtherIssueString;
    Boolean orderIssueValue;
    Boolean wrongAddressValue;
    Boolean paymentIssueValue;
    Boolean appIssueValue;
    Boolean otherIssueValue;

    Button submitComplaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_shopper);

        problemDescrip = (EditText) findViewById(R.id.problemInput);
        orderIssue = (CheckBox) findViewById(R.id.checkBoxComplaintGeneral);
        wrongAddress = (CheckBox) findViewById(R.id.wAddressCheck);
        paymentIssue = (CheckBox) findViewById(R.id.payIssue);
        orderOtherIssue = (EditText) findViewById(R.id.otherOrderCheck);
        appIssue = (CheckBox) findViewById(R.id.appIssueCheck);
        otherIssue = (CheckBox) findViewById(R.id.otherIssueCheck);

        submitComplaint = (Button) findViewById(R.id.submitComplaintBtn);

        submitComplaint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                problemDescripString = problemDescrip.getText().toString();
                orderOtherIssueString = orderOtherIssue.getText().toString();
                orderIssueValue = orderIssue.isChecked();
                wrongAddressValue = wrongAddress.isChecked();
                paymentIssueValue = paymentIssue.isChecked();
                appIssueValue = appIssue.isChecked();
                otherIssueValue = otherIssue.isChecked();

                System.out.println("Problem Description" + problemDescripString);

                Intent intent = new Intent(ComplaintsShopper.this, ShopperHomeScreen.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
         });
    }
}