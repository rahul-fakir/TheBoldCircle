package com.rahul.fakir.theboldcircle.UserData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.fakir.theboldcircle.R;

public class UserProfileActivity extends AppCompatActivity {
    private String username;
    private EncodeEmailToUsername encoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        encoder = new EncodeEmailToUsername(user.getEmail().toString());
        username = encoder.getUsername();

        final TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        final EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        final EditText etAddressOne = (EditText) findViewById(R.id.etAddressOne);
        final EditText etAddressTwo = (EditText) findViewById(R.id.etAddressTwo);
        final EditText etCity = (EditText) findViewById(R.id.etCity);
        final EditText etProvince = (EditText) findViewById(R.id.etProvince);
        final EditText etZipCode = (EditText) findViewById(R.id.etZipCode);
        final EditText etCountry = (EditText) findViewById(R.id.etCountry);
        final TextView tvStoreID = (TextView) findViewById(R.id.tvStoreID);
        final TextView tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        final TextView tvStoreAddress = (TextView) findViewById(R.id.tvStoreAddress);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(username).child("userProfile");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.child("firstName").getValue().toString());


                tvEmail.setText(user.getEmail().toString());
                etFirstName.setText(dataSnapshot.child("firstName").getValue().toString());
                etSurname.setText(dataSnapshot.child("lastName").getValue().toString());
                etPhoneNumber.setText(dataSnapshot.child("mobileNumber").getValue().toString());
                DataSnapshot purchasingOptions = dataSnapshot.child("purchasingOptions").child("shippingAddress");
                etAddressOne.setText(purchasingOptions.child("addressOne").getValue().toString());
                etAddressTwo.setText(purchasingOptions.child("addressTwo").getValue().toString());
                etCity.setText(purchasingOptions.child("city").getValue().toString());
                etProvince.setText(purchasingOptions.child("province").getValue().toString());
                etZipCode.setText(purchasingOptions.child("zipCode").getValue().toString());
                etCountry.setText(purchasingOptions.child("country").getValue().toString());
                tvStoreID.setText(dataSnapshot.child("purchasingOptions").child("defaultStore").getValue().toString());

                DatabaseReference storeRef = database.getReference("stores").child("storeDetails").child(dataSnapshot.child("purchasingOptions").child("defaultStore").getValue().toString());
                storeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        tvStoreName.setText(dataSnapshot.child("name").getValue().toString());
                        tvStoreAddress.setText(dataSnapshot.child("address").getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
