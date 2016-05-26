package com.rahul.fakir.theboldcircle.UserData;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;
import com.rahul.fakir.theboldcircle.HomeScreenActivity;
import com.rahul.fakir.theboldcircle.R;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        final TextView tvCreateProfile = (TextView) findViewById(R.id.tvCreateProfile);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvResetPassword = (TextView) findViewById(R.id.tvForgotPassword);
        final CheckBox cbxPersistSession = (CheckBox) findViewById(R.id.cbxPersistSession);


        /*
        CHECK FOR AUTH HERE
        */
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is signed in
            intentToHome();
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
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {

                                            Toast.makeText(LogInActivity.this, "Sign In Failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            System.out.println(task.getException().toString());
                                        } else {
                                            if (!cbxPersistSession.isChecked()) {
                                                FirebaseAuth.getInstance().signOut();
                                            }
                                            intentToHome();
                                        }
                                    }
                                });



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


    private void intentToHome(){
        Intent intent = new Intent(LogInActivity.this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }
}