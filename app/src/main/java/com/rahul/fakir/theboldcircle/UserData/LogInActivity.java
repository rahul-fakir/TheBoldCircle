package com.rahul.fakir.theboldcircle.UserData;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.rahul.fakir.theboldcircle.HomeScreenActivity;
import com.rahul.fakir.theboldcircle.R;

public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Backendless.initApp( LogInActivity.this, "1D58A6DB-C412-6AA4-FFBB-2E2A7EC0CB00", "CDBE2EF7-DC1E-0D16-FFD8-18D0DF281000", "v1" );
        final TextView tvCreateProfile = (TextView) findViewById(R.id.tvCreateProfile);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvResetPassword = (TextView) findViewById(R.id.tvForgotPassword);
        final CheckBox cbxPersistSession = (CheckBox) findViewById(R.id.cbxPersistSession);

        String userToken = UserTokenStorageFactory.instance().getStorage().get();

        if( userToken != null && !userToken.equals( "" ) )
        {
            Intent intentToMain = new Intent(LogInActivity.this, HomeScreenActivity.class);
            intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentToMain);
            finish();
        }



        tvCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToCreateProfile = new Intent(LogInActivity.this, CreateProfileActivity.class);
                intentToCreateProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentToCreateProfile);
            }
        });

        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToCreateProfile = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
                intentToCreateProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentToCreateProfile);
            }
        });

        assert btnLogin != null;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidationResult result = validateForm(etEmail.getText().toString(), 1);

                if (result.getStatus()){
                    result = validateForm(etPassword.getText().toString(), 3);
                    if (result.getStatus()) {


                        Backendless.UserService.login( etEmail.getText().toString(), etPassword.getText().toString(), new AsyncCallback<BackendlessUser>()
                        {
                            public void handleResponse( BackendlessUser user )
                            {
                                // user has been logged in
                                Intent intent = new Intent (LogInActivity.this, HomeScreenActivity.class);
                                startActivity(intent);
                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                // login failed, to get the error code call fault.getCode()
                               Toast.makeText(getApplicationContext(), fault.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }, cbxPersistSession.isChecked());

                    } else{
                        Toast.makeText(getApplicationContext(), result.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), result.getMessage(),
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