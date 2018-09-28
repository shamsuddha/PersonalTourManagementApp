package com.example.shamsuddha.tourmate;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class AddEventMemoriesActivity extends AppCompatActivity {

    ImageView imageViewMemory, imageViewMemoryUpload, imageViewMemoryTakePhoto;
    EditText memoryDetailsEditText;
    Button saveMemory;
    private String mPhotoData;
    FirebaseAuth firebaseAuth;
    private ImageView imageView, mImageViewMemory;
    private Uri filePath;
    FirebaseUser user;
    FirebaseStorage storage;
    private StorageReference storageReference;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MY_CAMERA_REQUEST_CODE = 2;
    private final int PICK_IMAGE_REQUEST = 71;
    private DatabaseReference roofRef, userRef, travelEventRef, travelEventIdRef, memoryRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_memories);
        getCameraPermission();


        storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        roofRef = FirebaseDatabase.getInstance().getReference();
        userRef = roofRef.child(user.getUid());
        travelEventRef = userRef.child("travelEvent");

        Intent intent = getIntent();
        String key = intent.getStringExtra("id");
        travelEventRef = userRef.child("travelEvent");
        memoryRef = travelEventRef.child(key).child("memories");




        imageViewMemoryTakePhoto = findViewById(R.id.imageViewMemoryTakePhoto);
        mImageViewMemory = findViewById(R.id.imageViewMemory);
        imageViewMemoryUpload = findViewById(R.id.imageViewMemoryUpload);
        memoryDetailsEditText = findViewById(R.id.memoryDetailsEditText);
        saveMemory = findViewById(R.id.saveMemory);


        // take photo
        imageViewMemoryTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                }
            }
        });

        //select photo
        imageViewMemoryUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });



        //saveMemory

        saveMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();



            }
        });


    }



    private void uploadFile() {

        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading File...");
            progressDialog.show();
            String keId = travelEventRef.getKey();
            StorageReference riversRef = storageReference.child("memories/").child(keId).child(filePath.getLastPathSegment());
            riversRef.putFile(filePath)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(AddEventMemoriesActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    Toast.makeText(AddEventMemoriesActivity.this, "File Added", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage(((int) progress) + "% Uploaded...");
                }
            });




            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    String caption = memoryDetailsEditText.getText().toString();
                    String keyId = memoryRef.push().getKey();
                    Memories memories = new Memories(keyId,caption,url);
                    memoryRef.child(keyId).setValue(memories);
                    /// after checking transfer to a view travel event activity
                    Intent i = new Intent(AddEventMemoriesActivity.this, DashboardActivity.class);
                    startActivity(i);



                }
            });




        } else {
            final ProgressDialog progresssDialog = new ProgressDialog(this);
            progresssDialog.setTitle("Uploading File...");
            progresssDialog.show();
            mImageViewMemory.setDrawingCacheEnabled(true);
            mImageViewMemory.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) mImageViewMemory.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();


            // generate a random string
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            String s = "";
            Random random = new Random();
            int randomLen = 1 + random.nextInt(9);
            for (int i = 0; i < randomLen; i++) {
                char c = alphabet.charAt(random.nextInt(26));
                s += c;

                // end generating random string

                String keId = travelEventRef.getKey();
                StorageReference mountainsRef = storageReference.child("memories/").child(keId).child(s);
                UploadTask uploadTask = mountainsRef.putBytes(data);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        progresssDialog.dismiss();
                        Toast.makeText(AddEventMemoriesActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progresssDialog.dismiss();
                        Toast.makeText(AddEventMemoriesActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddEventMemoriesActivity.this, ViewEventMemoriesActivity.class);
                        //   getDownloadUrl();
                        startActivity(i);

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progresssDialog.setMessage(((int) progress) + "% Uploaded...");

                    }
                });



                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        String caption = memoryDetailsEditText.getText().toString();
                        String keyId = memoryRef.push().getKey();
                        Memories memories = new Memories(keyId,caption,url);
                        memoryRef.child(keyId).setValue(memories);
                        /// after checking transfer to a view travel event activity
                        Intent i = new Intent(AddEventMemoriesActivity.this, ViewEventMemoriesActivity.class);
                        startActivity(i);

                    }
                });


            }

        }


    }




    private void getCameraPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == PICK_IMAGE_REQUEST  && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                mImageViewMemory.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPhotoData = encodeToBase64 (imageBitmap,Bitmap.CompressFormat.JPEG, 50);
            System.out.println(mPhotoData);
            mImageViewMemory.setImageBitmap(imageBitmap);
        }



    }



    //------------------------
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
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
                Intent home = new Intent(AddEventMemoriesActivity.this, MainActivity.class);
                startActivity(home);
                break;


            case R.id.action_add_travel_event:
                Intent addTravelEvent = new Intent(AddEventMemoriesActivity.this, AddTravelEventActivity.class);
                startActivity(addTravelEvent);
                break;
            case R.id.action_view_travel_event:
                Intent viewTravelEvent = new Intent(AddEventMemoriesActivity.this, ViewTravelEventActivity.class);
                startActivity(viewTravelEvent);
                break;
            case R.id.action_dashboard:
                Intent dashboard = new Intent(AddEventMemoriesActivity.this, DashboardActivity.class);
                startActivity(dashboard);
                break;
            case R.id.action_profile:
                Intent profile = new Intent(AddEventMemoriesActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;




            case R.id.action_logout:
                firebaseAuth.signOut();
                finish();
                Intent signOut = new Intent(AddEventMemoriesActivity.this, MainActivity.class);
                startActivity(signOut);
                break;



            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------

}
