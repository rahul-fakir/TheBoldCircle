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
import com.google.firebase.auth.FirebaseAuth;
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
                    FirebaseAuth auth = FirebaseAuth.getInstance();


                    auth.sendPasswordResetEmail(etEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Reset email has been sent. Please check your email",
                                                Toast.LENGTH_LONG).show();
                                        finish();
                                    }
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
