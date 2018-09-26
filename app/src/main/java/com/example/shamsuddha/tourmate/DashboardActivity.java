package com.example.shamsuddha.tourmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    TextView addTravelEventTextView, myTravelEventsTextView, weatherTextView, nearByPlacesTextView;
    ImageView mAddTravelEventImageView, myTravelEventsImageView, myProfileImageView, weatherImageView, nearByPlacesImageView,
            myMemoriesImageView;
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

        mAddTravelEventImageView = findViewById(R.id.addTravelEventImageView);
        myTravelEventsImageView = findViewById(R.id.myTravelEventsImageView);
       // myProfileImageView = findViewById(R.id.myProfileImageView);
        weatherImageView = findViewById(R.id.weatherImageView);
        nearByPlacesImageView = findViewById(R.id.nearByPlacesImageView);
        //myMemoriesImageView = findViewById(R.id.myMemoriesImageView);
        myTravelEventsTextView = findViewById(R.id.myTravelEventsTextView);

        addTravelEventTextView = findViewById(R.id.addTravelEventTextView);

        addTravelEventTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aas = new Intent(DashboardActivity.this, AddTravelEventActivity.class);
                startActivity(aas);
            }
        });


        mAddTravelEventImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aa = new Intent(DashboardActivity.this, AddTravelEventActivity.class);
                startActivity(aa);
            }
        });


        myTravelEventsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent travelEvent = new Intent(DashboardActivity.this, ViewTravelEventActivity.class);
                startActivity(travelEvent);
            }
        });




        weatherImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent travelEvent = new Intent(DashboardActivity.this, WeatherActivity.class);
                startActivity(travelEvent);
            }
        });


        nearByPlacesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent travelEvent = new Intent(DashboardActivity.this, MapsActivity.class);
                startActivity(travelEvent);
            }
        });




        myTravelEventsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog   dialog = new ProgressDialog(DashboardActivity.this);
                dialog.setMessage("Please Wait");
                Intent travelEvent = new Intent(DashboardActivity.this, ViewTravelEventActivity.class);
                startActivity(travelEvent);
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
