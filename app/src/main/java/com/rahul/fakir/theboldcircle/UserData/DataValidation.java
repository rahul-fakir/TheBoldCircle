package com.rahul.fakir.theboldcircle.UserData;

import android.util.Patterns;

import java.util.regex.Pattern;

public class DataValidation {
    private String userInput;
    private boolean validationStatus = true;
    private String errorMessage = "";

    public DataValidation(String userInput, int validationCode){
        this.userInput = userInput;

        switch (validationCode){
            case 0: {
                validateEmptyString(userInput);
                break;
            }
            case 1: {
                validateEmail(userInput);
                break;
            }
            case 2: {
                validateMobileNumber(userInput);
                break;
            }
            case 3: {
                validatePassword(userInput);
                break;
            }
        }
    }

    private void validateEmptyString(String input){
        if ((input == null) || (input.isEmpty())) {
            validationStatus = false;
            errorMessage = "Empty input";
        }
    }

    private void validateEmail(String email){
        validationStatus = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!validationStatus) {
            errorMessage = "Invalid email format";
        }
    }

    private void validateMobileNumber(String mobileNumber){
        validationStatus = Patterns.PHONE.matcher(mobileNumber).matches();
        if (!validationStatus) {
            errorMessage = "Invalid mobile number";
        }
    }

    private void validatePassword(String password ) {
        final Pattern hasUppercase = Pattern.compile("[A-Z]");
        final Pattern hasLowercase = Pattern.compile("[a-z]");
        final Pattern hasNumber = Pattern.compile("\\d");


        if (password == null) {
            validationStatus = false;
            errorMessage = "Password is null";
            return;
        }
        if (password.isEmpty()) {
            validationStatus = false;
            errorMessage = "Password is empty";
            return;
        }
        if (password.length() < 8) {
            validationStatus = false;
            errorMessage = "Password is too short. Needs to have 8 characters";
            return;
        }
        if (!hasUppercase.matcher(password).find()) {
            validationStatus = false;
            errorMessage = "Password needs an uppercase letter";
            return;
        }
        if (!hasLowercase.matcher(password).find()) {
            validationStatus = false;
            errorMessage = "Password needs a lowercase letter";
            return;
        }
        if (!hasNumber.matcher(password).find()) {
            validationStatus = false;
            errorMessage = "Password needs a number ";
            return;
        }
    }

    public boolean getValidationStatus(){
        return validationStatus;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}