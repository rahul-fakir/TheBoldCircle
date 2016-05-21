package com.rahul.fakir.theboldcircle.UserData;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rahul.fakir.theboldcircle.R;



public class CreateProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //variable decleration
        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        final EditText etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etConfirmEmail = (EditText) findViewById(R.id.etConfirmEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        final Button btnCreateProfile = (Button) findViewById(R.id.btnCreateProfile);

        //create profile button click handler
        assert btnCreateProfile != null;
        btnCreateProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ValidationResult result = validateForm(etFirstName.getText().toString(), 0);

                if (result.getStatus()){
                    result = validateForm(etSurname.getText().toString(), 0);
                    if (result.getStatus()) {
                        result = validateForm(etMobileNumber.getText().toString(), 2);
                        if (result.getStatus()) {
                            result = validateForm(etEmail.getText().toString(), 1);
                            if (result.getStatus()) {
                                result = validateForm(etPassword.getText().toString(), 3);
                                if (result.getStatus()) {
                                    if (etEmail.getText().toString().equals(etConfirmEmail.getText().toString())) {
                                        if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {

                                            //database user created
                                            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                            mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            // If sign in fails, display a message to the user. If sign in succeeds
                                                            // the auth state listener will be notified and logic to handle the
                                                            // signed in user can be handled in the listener.
                                                            if (!task.isSuccessful()) {
                                                                Toast.makeText(CreateProfileActivity.this, "Sign-up Failed.",
                                                                        Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                //userprofile created

                                                                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                                                                EncodeEmailToUsername username = new EncodeEmailToUsername(etEmail.getText().toString());
                                                                DatabaseReference newUserRef = mDatabase.getReference("users").child(username.getUsername());
                                                                newUserRef.child("firstName").setValue(etFirstName.getText().toString().toLowerCase());
                                                                newUserRef.child("lastName").setValue(etSurname.getText().toString().toLowerCase());
                                                                newUserRef.child("mobileNumber").setValue(etMobileNumber.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (!task.isSuccessful()) {
                                                                            Toast.makeText(CreateProfileActivity.this, "Sign-up Failed.",
                                                                                    Toast.LENGTH_SHORT).show();
                                                                        } else
                                                                        {
                                                                            Toast.makeText(CreateProfileActivity.this, "Your profile is created. Please log in",
                                                                                    Toast.LENGTH_SHORT).show();
                                                                            FirebaseAuth.getInstance().signOut();
                                                                            finish();
                                                                        }
                                                                    }
                                                                });

                                                            }

                                                            // ...
                                                        }
                                                    });

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Passwords do not match",
                                                    Toast.LENGTH_LONG).show();
                                        }

                                    } else{
                                        Toast.makeText(getApplicationContext(), "Emails do not match",
                                                Toast.LENGTH_LONG).show();
                                    }

                                } else{
                                    Toast.makeText(getApplicationContext(), result.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }

                            } else{
                                Toast.makeText(getApplicationContext(), result.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }

                        } else{
                            Toast.makeText(getApplicationContext(), result.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "Surname cannot be blank",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "First name cannot be blank",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public ValidationResult validateForm(String input, int code){
        DataValidation validationCheck = new DataValidation(input, code);
        ValidationResult result = new ValidationResult(validationCheck.getValidationStatus(), validationCheck.getErrorMessage());
        return result;
    }
}

