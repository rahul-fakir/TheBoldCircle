package com.rahul.fakir.theboldcircle.UserData;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
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
                    Backendless.UserService.restorePassword( etEmail.getText().toString(), new AsyncCallback<Void>()
                    {
                        public void handleResponse( Void response )
                        {
                            // Backendless has completed the operation - an email has been sent to the user
                            Toast.makeText(getApplicationContext(), "Your password reset has been processed. Please check your email",
                                    Toast.LENGTH_LONG).show();
                        }

                        public void handleFault( BackendlessFault fault )
                        {
                            // password revovery failed, to get the error code call fault.getCode()
                            Toast.makeText(getApplicationContext(), fault.toString(),
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
