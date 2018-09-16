package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;
    TextView travelEventTextView, expenceTextView, nearByPlacesTextView, weatherTextView, memoriesTextView, profileTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent i = new Intent(DashboardActivity.this, MainActivity.class);
            startActivity(i);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();



        travelEventTextView = findViewById(R.id.travelEventTextView);
        expenceTextView = findViewById(R.id.expenceTextView);
        nearByPlacesTextView = findViewById(R.id.nearByPlacesTextView);
        weatherTextView = findViewById(R.id.weatherTextView);
        memoriesTextView = findViewById(R.id.memoriesTextView);
        profileTextView = findViewById(R.id.profileTextView);






    }



    //------------------ Menu Section ------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
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
                Intent home = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(home);
                break;

            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(signOut);
                break;



            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------







}
