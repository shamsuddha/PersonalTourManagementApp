package com.example.shamsuddha.tourmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    TextView mEditProfileTextView;
    ImageView profile_picture;
    FirebaseAuth firebaseAuth;
    TextView mEditProfilePicTextView, emailTextView, contactNumberTextView, addressTextView;
    Button editProfile;
    FirebaseUser user;
    private StorageReference storageReference;

    private DatabaseReference roofRef, userRef, profileRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());
        profileRef = userRef.child("profileInfo");





        mEditProfilePicTextView = findViewById(R.id.editProfilePicTextView);
        emailTextView = findViewById(R.id.emailTextView);
        contactNumberTextView = findViewById(R.id.contactNumberTextView);
        addressTextView = findViewById(R.id.addressTextView);
        profile_picture = findViewById(R.id.profile_picture);
        editProfile = findViewById(R.id.editProfile);





        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Profile profile = dataSnapshot.getValue(Profile.class);

                String name = profile.getName();
                String contactNumber = profile.getContactNumber();
                String address = profile.getAddress();


                emailTextView.setText(name);
                contactNumberTextView.setText(contactNumber);
                addressTextView.setText(address);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });












        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent i = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(i);
        }

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mEditProfilePicTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfilePic = new Intent(ProfileActivity.this, EditProfilePictureActivity.class)
                        .putExtra("id", user.getUid() )
                        ;
                startActivity(editProfilePic);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(ProfileActivity.this, EditProfileActivity.class)
                        .putExtra("id", user.getUid() )
                        ;
                startActivity(editProfile);
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
                Intent home = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(home);
                break;

            case R.id.action_add_travel_event:
                Intent addTravelEvent = new Intent(ProfileActivity.this, AddTravelEventActivity.class);
                startActivity(addTravelEvent);
                break;
            case R.id.action_view_travel_event:
                Intent viewTravelEvent = new Intent(ProfileActivity.this, ViewTravelEventActivity.class);
                startActivity(viewTravelEvent);
                break;
            case R.id.action_dashboard:
                Intent dashboard = new Intent(ProfileActivity.this, DashboardActivity.class);
                startActivity(dashboard);
                break;
            case R.id.action_profile:
                Intent profile = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;

            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(signOut);
                break;

            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------




}
