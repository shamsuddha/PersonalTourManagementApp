package com.example.shamsuddha.tourmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameEditText, userEmailAddressEditText, userPasswordEditText;
    Button signUpButton, loginButton;
    FirebaseAuth firebaseAuth;
    TextView loginTextView;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            //profile activity
            finish();
            Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(i);

        }


        userEmailAddressEditText = findViewById(R.id.userEmailAddressEditText);
        userPasswordEditText = findViewById(R.id.userPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        loginTextView = findViewById(R.id.loginTextView);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }



    private void registerUser() {

        String email = userEmailAddressEditText.getText().toString();
        String password = userPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter an email address", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // user is successfully registered
                            finish();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Could not register, try again",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }









    //------------------ Menu Section ------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_before_login,menu);
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
                Intent home = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.action_login:
                Intent login = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(login);
                break;
            case R.id.action_register:
                Intent register = new Intent(SignUpActivity.this, SignUpActivity.class);
                startActivity(register);
                break;
            default:
                Toast.makeText(this, "Something Went ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------ Menu Section End --------------------------------







}

