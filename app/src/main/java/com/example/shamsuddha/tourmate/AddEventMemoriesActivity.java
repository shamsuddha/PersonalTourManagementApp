package com.example.shamsuddha.tourmate;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddEventMemoriesActivity extends AppCompatActivity {

    ImageView imageViewMemory, imageViewMemoryUpload, imageViewMemoryTakePhoto;
    EditText memoryDetailsEditText;
    Button saveMemory;
    private String mPhotoData;
    FirebaseAuth firebaseAuth;
    private ImageView imageView, mImageViewMemory;
    private Uri filePath;
    FirebaseStorage storage;
    private StorageReference storageReference;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MY_CAMERA_REQUEST_CODE = 2;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_memories);

        getCameraPermission();

        storageReference = FirebaseStorage.getInstance().getReference();


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



    private void uploadFile(){

        if (filePath != null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading File...");
            progressDialog.show();
           StorageReference riversRef = storageReference.child("images/").child(filePath.getLastPathSegment());

            riversRef.putFile(filePath)

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(AddEventMemoriesActivity.this,exception.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference riversRef = storageReference.child("images/").child(filePath.getLastPathSegment());
                    progressDialog.dismiss();

                   // Uri downlaodUri  =  riversRef.getDownloadUrl();;

                    Toast.makeText(AddEventMemoriesActivity.this,"File Uploaded", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage(((int) progress) + "% Uploaded...");

                }
            });


        }

        else{
            final ProgressDialog progresssDialog = new ProgressDialog(this);
            progresssDialog.setTitle("Uploading File...");
            progresssDialog.show();
            mImageViewMemory.setDrawingCacheEnabled(true);
            mImageViewMemory.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) mImageViewMemory.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            StorageReference mountainsRef = storageReference.child("images/"+data);
            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    progresssDialog.dismiss();
                    Toast.makeText(AddEventMemoriesActivity.this,exception.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progresssDialog.dismiss();
                    Toast.makeText(AddEventMemoriesActivity.this,"File Uploaded", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddEventMemoriesActivity.this,ViewEventMemoriesActivity.class);
                    startActivity(i);

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progresssDialog.setMessage(((int) progress) + "% Uploaded...");

                }
            });
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

        if (requestCode == PICK_IMAGE_REQUEST  && resultCode == RESULT_OK && data != null && data.getData() != null ) {
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
