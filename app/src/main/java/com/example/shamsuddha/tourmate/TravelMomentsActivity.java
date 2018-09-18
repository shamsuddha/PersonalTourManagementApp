package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TravelMomentsActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_moments);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent i = new Intent(TravelMomentsActivity.this, MainActivity.class);
            startActivity(i);
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();














    }
}
