package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    ListView mTravelEventListView;
    TravelEventAdapter travelEventAdapter;
    List<TravelEvent> travelEventList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_travel_event);


        mTravelEventListView = findViewById(R.id.travelEventListView);

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
                    TravelEvent travelEvent = d.getValue(TravelEvent.class);
                    travelEventList.add(travelEvent);

                }
                travelEventAdapter = new TravelEventAdapter( ViewTravelEventActivity.this, travelEventList);
                mTravelEventListView.setAdapter(travelEventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewTravelEventActivity.this,"Failed!!",Toast.LENGTH_LONG).show();
            }
        });





        mTravelEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent iaa = new Intent(ViewTravelEventActivity.this, TravelEeventDetaisActivity.class);
                startActivity(iaa);
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
                Intent home = new Intent(ViewTravelEventActivity.this, MainActivity.class);
                startActivity(home);
                break;

            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(ViewTravelEventActivity.this, MainActivity.class);
                startActivity(signOut);
                break;



            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------




}
