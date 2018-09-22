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
    Button eventMemoriesButton, addTravelExpenseButton, viewTravelExpenseButton, editEventButton, deleteEventButton;
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




        final String id = getIntent().getStringExtra("id");
        user = FirebaseAuth.getInstance().getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());
       // travelEventRef = userRef.child("travelEvent").getKey().contentEquals();

       // travelEventIDRef = travelEventRef.getKey().e;






        eventMemoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memories = new Intent(TravelEeventDetaisActivity.this,AddEventMemoriesActivity.class);
                startActivity(memories);


            }
        });

        addTravelExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent expense = new Intent(TravelEeventDetaisActivity.this,AddTravelExpenseActivity.class);
                startActivity(expense);
            }
        });


        viewTravelExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent expense = new Intent(TravelEeventDetaisActivity.this,ViewTravelExpenseActivity.class);
                startActivity(expense);
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
