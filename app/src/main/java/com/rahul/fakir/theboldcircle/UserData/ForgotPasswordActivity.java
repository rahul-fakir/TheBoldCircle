package com.rahul.fakir.theboldcircle.UserData;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rahul.fakir.theboldcircle.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ValidationResult result = validateForm(etEmail.getText().toString(), 1);

                if (result.getStatus()) {

                    Firebase ref = new Firebase("https://the-bold-circle.firebaseio.com");
                    ref.resetPassword(etEmail.getText().toString(), new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            // password reset email sent
                            Toast.makeText(getApplicationContext(), "Reset email has been sent",
                                    Toast.LENGTH_LONG).show();
                            Intent intentToCreateProfile = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
                            intentToCreateProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentToCreateProfile);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // error encountered
                            Toast.makeText(getApplicationContext(), firebaseError.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                } else
                {
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
