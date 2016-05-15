package com.rahul.fakir.theboldcircle.UserData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rahul.fakir.theboldcircle.R;

import java.util.HashMap;
import java.util.Map;

public class CreateProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //allows this activity to connect to Firebase
        Firebase.setAndroidContext(this);

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
                                            final Firebase ref = new Firebase("https://the-bold-circle.firebaseio.com");

                                            ref.createUser(etEmail.getText().toString(), etPassword.getText().toString(),
                                                    new Firebase.ValueResultHandler<Map<String, Object>>() {

                                                        //on user creation success
                                                        @Override
                                                        public void onSuccess(Map<String, Object> result) {
                                                            Firebase userRef = ref.child("users");

                                                            Map<String, String> userObject = new HashMap<String, String>();
                                                            userObject.put("first_name", etFirstName.getText().toString().toLowerCase());
                                                            userObject.put("surname", etSurname.getText().toString().toLowerCase());
                                                            userObject.put("mobile_number", etMobileNumber.getText().toString());
                                                            userObject.put("email", etEmail.getText().toString().toLowerCase());
                                                            userRef.push().setValue(userObject);

                                                            Toast.makeText(getApplicationContext(), "User successfully created. Please log in",
                                                                    Toast.LENGTH_LONG).show();
                                                            finish();
                                                        }

                                                        //on user creation failure
                                                        @Override
                                                        public void onError(FirebaseError firebaseError) {
                                                            Toast.makeText(getApplicationContext(), firebaseError.toString(),
                                                                    Toast.LENGTH_LONG).show();
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

