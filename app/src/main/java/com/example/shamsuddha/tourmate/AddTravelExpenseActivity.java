package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;

public class AddTravelExpenseActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    EditText expenseDetails, expenseAmount;
    private DatabaseReference roofRef, userRef, travelEventRef, travelEventIdRef, travelEventExpenseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel_expense);

        expenseDetails = findViewById(R.id.expenseDetails);
        expenseAmount = findViewById(R.id.expenseAmount);
        user = FirebaseAuth.getInstance().getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());
        Intent intent = getIntent();
        String key = intent.getStringExtra("id");
        travelEventRef = userRef.child("travelEvent");
        travelEventExpenseRef = travelEventRef.child(key).child("travelExpense");
        // travelEventExpenseRef = travelEventRef.child(KeyId).child("travelExpense");
        // String keyId = travelEventRef.push().getKey();
    }


    public void addExpense(View view) {

        Intent intent = getIntent();
        String keys = intent.getStringExtra("id");

        String xpenseDetails = expenseDetails.getText().toString();
        String xpenseAmount = expenseAmount.getText().toString();
        String keyId = travelEventExpenseRef.push().getKey();
        TravelExpense travelExpense = new TravelExpense(keyId,xpenseDetails,xpenseAmount);
        travelEventExpenseRef.child(keyId).setValue(travelExpense);
        /// after checking transfer to a view travel event activity
        Intent i = new Intent(AddTravelExpenseActivity.this, ViewTravelExpenseActivity.class)
                .putExtra("id", keys);
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
                Intent home = new Intent(AddTravelExpenseActivity.this, MainActivity.class);
                startActivity(home);
                break;

            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(AddTravelExpenseActivity.this, MainActivity.class);
                startActivity(signOut);
                break;



            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------




}
