package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TravelEeventDetaisActivity extends AppCompatActivity {

    TextView eventDestinationTextView, eventStartingDate, eventEndingDate, estimatedBudgetTextView;
    Button eventMemoriesButton, addTravelExpenseButton, viewTravelExpenseButton, editEventButton, deleteEventButton,
            showEventMemories;
    FirebaseAuth firebaseAuth;
    private DatabaseReference roofRef, userRef, travelEventRef, travelEventIDRef;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_eevent_detais);



        eventDestinationTextView = findViewById(R.id.eventDestinationTextView);
        eventStartingDate = findViewById(R.id.eventStartingDate);
        eventEndingDate = findViewById(R.id.eventEndingDate);
        estimatedBudgetTextView = findViewById(R.id.estimatedBudgetTextView);


        eventMemoriesButton = findViewById(R.id.eventMemoriesButton);
        addTravelExpenseButton = findViewById(R.id.addTravelExpenseButton);
        viewTravelExpenseButton = findViewById(R.id.viewTravelExpenseButton);
        editEventButton = findViewById(R.id.editEventButton);
        deleteEventButton = findViewById(R.id.deleteEventButton);
        showEventMemories = findViewById(R.id.showEventMemories);



        final String id = getIntent().getStringExtra("id");
        user = FirebaseAuth.getInstance().getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());


       // travelEventRef = userRef.child("travelEvent").getKey();

       // travelEventIDRef = travelEventRef.getKey().e;


        editEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(TravelEeventDetaisActivity.this, AddTravelEventActivity.class);
                in.putExtra("id", id);
                startActivity(in);
            }
        });






        eventMemoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memories = new Intent(TravelEeventDetaisActivity.this,AddEventMemoriesActivity.class)
                        .putExtra("id", id);
                startActivity(memories);


            }
        });

        addTravelExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent expense = new Intent(TravelEeventDetaisActivity.this,AddTravelExpenseActivity.class)
                        .putExtra("id", id);
                startActivity(expense);
            }
        });


        viewTravelExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent expense = new Intent(TravelEeventDetaisActivity.this,ViewTravelExpenseActivity.class)
                        .putExtra("id", id);
                startActivity(expense);
            }
        });



        showEventMemories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memories = new Intent(TravelEeventDetaisActivity.this,ViewEventMemoriesActivity.class)
                        .putExtra("id", id);
                startActivity(memories);
            }
        });

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
                Intent home = new Intent(TravelEeventDetaisActivity.this, MainActivity.class);
                startActivity(home);
                break;


            case R.id.action_add_travel_event:
                Intent addTravelEvent = new Intent(TravelEeventDetaisActivity.this, AddTravelEventActivity.class);
                startActivity(addTravelEvent);
                break;
            case R.id.action_view_travel_event:
                Intent viewTravelEvent = new Intent(TravelEeventDetaisActivity.this, ViewTravelEventActivity.class);
                startActivity(viewTravelEvent);
                break;
            case R.id.action_dashboard:
                Intent dashboard = new Intent(TravelEeventDetaisActivity.this, DashboardActivity.class);
                startActivity(dashboard);
                break;
            case R.id.action_profile:
                Intent profile = new Intent(TravelEeventDetaisActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;




            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(TravelEeventDetaisActivity.this, MainActivity.class);
                startActivity(signOut);
                break;



            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------

}
