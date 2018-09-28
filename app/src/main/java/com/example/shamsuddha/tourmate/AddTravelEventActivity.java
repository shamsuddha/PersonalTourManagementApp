package com.example.shamsuddha.tourmate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddTravelEventActivity extends AppCompatActivity {

    private EditText mTravelDestinationEditText, mEstimatedBudgetEditText, mFromDateEditText, mToDateEditText;
    private DatabaseReference roofRef, userRef, travelEventRef;
    private FirebaseUser user;
    private DatePickerDialog datePickerDialog;
    private TextView addTravelEventText;
    private Button addTravelButton;
    List<TravelEvent> travelEventList = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel_event);

        mTravelDestinationEditText = findViewById(R.id.travelDestinationEditText);
        mEstimatedBudgetEditText = findViewById(R.id.estimatedBudgetEditText);
        mFromDateEditText = findViewById(R.id.fromDateEditText);
        mToDateEditText = findViewById(R.id.toDateEditText);
        addTravelButton = findViewById(R.id.addTravelButton);
        addTravelEventText = findViewById(R.id.addTravelEventText);

        user = FirebaseAuth.getInstance().getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());
        travelEventRef = userRef.child("travelEvent");

        mFromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFromDate();
            }
        });
        mToDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectToDate();
            }
        });




        }



    private void selectFromDate() {
        datePickerDialog = new DatePickerDialog(AddTravelEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mFromDateEditText.setText(dayOfMonth+"/"+month+"/"+year);
                // Toast.makeText(AddTravelEventActivity.this, "Date Selected :"+dayOfMonth+":"+month+":"+year, Toast.LENGTH_SHORT).show();
            }
        }, 2018, 9, 26);
        datePickerDialog.show();
    }

    private void selectToDate() {
        datePickerDialog = new DatePickerDialog(AddTravelEventActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mToDateEditText.setText(dayOfMonth+"/"+month+"/"+year);
                // Toast.makeText(AddTravelEventActivity.this, "Date Selected :"+dayOfMonth+":"+month+":"+year, Toast.LENGTH_SHORT).show();
            }
        }, 2018, 9, 26);
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




    //------------------ Menu Section ------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_after_login,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_home:
                Intent home = new Intent(AddTravelEventActivity.this, MainActivity.class);
                startActivity(home);
                break;


            case R.id.action_add_travel_event:
                Intent addTravelEvent = new Intent(AddTravelEventActivity.this, AddTravelEventActivity.class);
                startActivity(addTravelEvent);
                break;
            case R.id.action_view_travel_event:
                Intent viewTravelEvent = new Intent(AddTravelEventActivity.this, ViewTravelEventActivity.class);
                startActivity(viewTravelEvent);
                break;
            case R.id.action_dashboard:
                Intent dashboard = new Intent(AddTravelEventActivity.this, DashboardActivity.class);
                startActivity(dashboard);
                break;
            case R.id.action_profile:
                Intent profile = new Intent(AddTravelEventActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;




            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(AddTravelEventActivity.this, MainActivity.class);
                startActivity(signOut);
                break;



            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------

}









