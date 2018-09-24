package com.example.shamsuddha.tourmate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class AddTravelEventActivity extends AppCompatActivity {

    EditText mTravelDestinationEditText, mEstimatedBudgetEditText, mFromDateEditText, mToDateEditText;
    private DatabaseReference roofRef, userRef, travelEventRef;
    private FirebaseUser user;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel_event);

        mTravelDestinationEditText = findViewById(R.id.travelDestinationEditText);
        mEstimatedBudgetEditText = findViewById(R.id.estimatedBudgetEditText);
        mFromDateEditText = findViewById(R.id.fromDateEditText);
        mToDateEditText = findViewById(R.id.toDateEditText);

        user = FirebaseAuth.getInstance().getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());
        travelEventRef = userRef.child("travelEvent");



        mFromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFromDate ();
            }
        });






    }

    private void selectFromDate() {

        datePickerDialog = new DatePickerDialog(AddTravelEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(AddTravelEventActivity.this, "Date Selected :"+dayOfMonth+":"+month+":"+year, Toast.LENGTH_SHORT).show();
            }
        }, 2018, 8, 27);

        // mFromDateEditText.setText();
        datePickerDialog.show();

    }

    public void addTravelEvent(View view) {

        String travelDestination = mTravelDestinationEditText.getText().toString();
        String estimatedBudget = mEstimatedBudgetEditText.getText().toString();
        String fromDate = mFromDateEditText.getText().toString();
        String toDate = mToDateEditText.getText().toString();

        String keyId = travelEventRef.push().getKey();

        TravelEvent travelEvent = new TravelEvent(keyId,travelDestination,estimatedBudget,fromDate,toDate);
        travelEventRef.child(keyId).setValue(travelEvent);


        /// after checking transfer to a view travel event activity

        Intent i = new Intent(AddTravelEventActivity.this, ViewTravelEventActivity.class);
        startActivity(i);



    }


}
