package com.rahul.fakir.theboldcircle.UserData;

/**
 * Created by rahul.fakir on 2016/05/20.
 */
public class EncodeEmailToUsername {
    private String input;

    public EncodeEmailToUsername(String email){
        input = email;
        convertEmailToUsername();
    }

    private void convertEmailToUsername() {
        input = input.replace(".","0");
        input = input.replace("#", "1");
        input = input.replace("$", "2");
        input = input.replace("[", "3");
        input = input.replace("]", "4");
    }

    public String getUsername() {
        return input;
    }
}
