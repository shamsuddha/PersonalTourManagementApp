package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditProfileActivity extends AppCompatActivity {

    EditText profileNameEditText, profileEmailEditText, profileContactEditText, profileAddressEditText;
    Button updateProfileButton;
    private StorageReference storageReference;
    private String mPhotoData;
    FirebaseAuth firebaseAuth;
    private DatabaseReference roofRef, userRef, profileRef;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());

        Intent intent = getIntent();
        String key = intent.getStringExtra("id");
        profileRef = userRef.child("profileInfo");

        profileNameEditText = findViewById(R.id.profileNameEditText);
        profileEmailEditText = findViewById(R.id.profileEmailEditText);
        profileContactEditText = findViewById(R.id.profileContactEditText);
        profileAddressEditText = findViewById(R.id.profileAddressEditText);
        profileNameEditText = findViewById(R.id.profileNameEditText);



        profileEmailEditText.setText(user.getEmail());


        updateProfileButton = findViewById(R.id.updateProfileButton);
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = profileNameEditText.getText().toString();
                String contactNumber = profileContactEditText.getText().toString();
                String address = profileAddressEditText.getText().toString();

                String keyId = profileRef.push().getKey();
                Profile profile = new Profile(keyId,name, contactNumber, address);
                profileRef.setValue(profile);
                /// after checking transfer to a view travel event activity
                Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(i);


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
                Intent home = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(home);
                break;


            case R.id.action_add_travel_event:
                Intent addTravelEvent = new Intent(EditProfileActivity.this, AddTravelEventActivity.class);
                startActivity(addTravelEvent);
                break;
            case R.id.action_view_travel_event:
                Intent viewTravelEvent = new Intent(EditProfileActivity.this, ViewTravelEventActivity.class);
                startActivity(viewTravelEvent);
                break;
            case R.id.action_dashboard:
                Intent dashboard = new Intent(EditProfileActivity.this, DashboardActivity.class);
                startActivity(dashboard);
                break;
            case R.id.action_profile:
                Intent profile = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;




            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(signOut);
                break;



            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------
}
