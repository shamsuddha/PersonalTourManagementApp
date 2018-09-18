package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewTravelEventActivity extends AppCompatActivity {

    private DatabaseReference roofRef, userRef, travelEventRef;
    FirebaseAuth firebaseAuth;
    ListView travelEventListView;
    TravelEventAdapter travelEventAdapter;
    List<TravelEvent> travelEventList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_travel_event);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent i = new Intent(ViewTravelEventActivity.this, MainActivity.class);
            startActivity(i);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());
        travelEventRef = userRef.child("travelEvent");

        travelEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                travelEventList.clear();
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    TravelEvent te = d.getValue(TravelEvent.class);
                    travelEventList.add(te);

                }
                travelEventAdapter = new TravelEventAdapter( ViewTravelEventActivity.this, travelEventList);
                travelEventListView.setAdapter(travelEventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewTravelEventActivity.this,"Failed!!",Toast.LENGTH_LONG).show();
            }
        });










    }
}
