package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewEventMemoriesActivity extends AppCompatActivity {
    ImageView eventMemories;
    ListView memoriyListView;
    TextView eventMemoriesCaptionTextView;
    FirebaseAuth firebaseAuth;
    List<Memories> memoryList = new ArrayList<>();
    MemoryAdapter memoryAdapter;
    private DatabaseReference roofRef, userRef, travelEventRef, memoryRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_memories);

        memoriyListView = findViewById(R.id.memoriyListView);
        eventMemories = findViewById(R.id.eventMemories);
        eventMemoriesCaptionTextView = findViewById(R.id.eventMemoriesCaptionTextView);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent i = new Intent(ViewEventMemoriesActivity.this, MainActivity.class);
            startActivity(i);
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());

        Intent intent = getIntent();
        String key = intent.getStringExtra("id");
        travelEventRef = userRef.child("travelEvent");
        memoryRef = travelEventRef.child(key).child("memories");

        memoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                memoryList.clear();
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Memories memories = d.getValue(Memories.class);
                    memoryList.add(memories);
                }
                memoryAdapter  = new MemoryAdapter( ViewEventMemoriesActivity.this, memoryList);
                memoriyListView.setAdapter(memoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewEventMemoriesActivity.this,"Failed!!",Toast.LENGTH_LONG).show();
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
                Intent home = new Intent(ViewEventMemoriesActivity.this, MainActivity.class);
                startActivity(home);
                break;


            case R.id.action_add_travel_event:
                Intent addTravelEvent = new Intent(ViewEventMemoriesActivity.this, AddTravelEventActivity.class);
                startActivity(addTravelEvent);
                break;
            case R.id.action_view_travel_event:
                Intent viewTravelEvent = new Intent(ViewEventMemoriesActivity.this, ViewTravelEventActivity.class);
                startActivity(viewTravelEvent);
                break;
            case R.id.action_dashboard:
                Intent dashboard = new Intent(ViewEventMemoriesActivity.this, DashboardActivity.class);
                startActivity(dashboard);
                break;
            case R.id.action_profile:
                Intent profile = new Intent(ViewEventMemoriesActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;




            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(ViewEventMemoriesActivity.this, MainActivity.class);
                startActivity(signOut);
                break;



            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------


}
