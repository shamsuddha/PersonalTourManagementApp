package com.example.shamsuddha.tourmate;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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

          registerForContextMenu(mTravelEventListView);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
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
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    TravelEvent travelEvent = d.getValue(TravelEvent.class);
                    travelEventList.add(travelEvent);
                }
                travelEventAdapter = new TravelEventAdapter(ViewTravelEventActivity.this, travelEventList);
                mTravelEventListView.setAdapter(travelEventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewTravelEventActivity.this, "Failed!!", Toast.LENGTH_LONG).show();
            }
        });

        mTravelEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TravelEvent travelEvent = travelEventList.get(position);
                String id = travelEvent.getId();
                Intent in = new Intent(ViewTravelEventActivity.this, TravelEeventDetaisActivity.class);
                in.putExtra("id", id);
                startActivity(in);
            }
        });



    /*   mTravelEventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final int positions = position;
                final Dialog dialog = new Dialog(ViewTravelEventActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.delete_confirmation);
                dialog.show();
                Button mYesConfirmation = dialog.findViewById(R.id.yesConfrimation);
                Button mNoConfirmation = dialog.findViewById(R.id.noConfirmation);

                mYesConfirmation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        TravelEvent travelEvent = travelEventList.get(positions);

                        String travelEventId = travelEvent.getId();
                        travelEventRef.child(travelEventId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ViewTravelEventActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        dialog.dismiss();

                    }
                });
                mNoConfirmation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ViewTravelEventActivity.this, "Backed to your travel event.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });




                return false;
            }
        });*/




    }


    //------------------ Menu Section ------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_after_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.emp_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int positions = info.position;
        String empId = travelEventList.get(positions).getId();
        // String empId = travelEventList.get(position).getId();
        switch (item.getItemId()){
            case R.id.edit:
                startActivity(new Intent(ViewTravelEventActivity.this, EditTravelEventsActivity.class)
                        .putExtra("id",empId));
                break;

        }
        return super.onContextItemSelected(item);
    }

}



