package com.rahul.fakir.theboldcircle.UserData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.rahul.fakir.theboldcircle.R;

import java.util.HashMap;
import java.util.Map;

public class CreateProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Backendless.initApp( CreateProfileActivity.this, "1D58A6DB-C412-6AA4-FFBB-2E2A7EC0CB00", "CDBE2EF7-DC1E-0D16-FFD8-18D0DF281000", "v1" );


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


                                            BackendlessUser user = new BackendlessUser();
                                            user.setProperty( "email", etEmail.getText().toString() );
                                            user.setProperty("name", etFirstName.getText().toString());
                                            user.setProperty("surname", etSurname.getText().toString());
                                            user.setProperty("phoneNumber", etMobileNumber.getText().toString());
                                            user.setPassword( etPassword.getText().toString() );

                                            Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>()
                                            {
                                                public void handleResponse( BackendlessUser registeredUser )
                                                {
                                                    // user has been registered and now can login
                                                    Toast.makeText(getApplicationContext(), "User successfully created",
                                                            Toast.LENGTH_LONG).show();
                                                    finish();
                                                }

                                                public void handleFault( BackendlessFault fault )
                                                {
                                                    // an error has occurred, the error code can be retrieved with fault.getCode()
                                                    Toast.makeText(getApplicationContext(), fault.toString(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            } );


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

