package com.rahul.fakir.theboldcircle.ProductData.Checkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.fakir.theboldcircle.HomeScreenActivity;
import com.rahul.fakir.theboldcircle.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    private double productsTotal = 0.0, servicesTotal = 0.0, taxTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        RelativeLayout lytShippingAddress = (RelativeLayout) findViewById(R.id.lytShippingDetails);
        TextView tvPaymentDetails = (TextView) findViewById(R.id.tvPaymentDetails);
        Button btnCompletPurchase = (Button) findViewById(R.id.btnCompletePurchase);
        btnCompletPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < CheckoutActivity.serviceList.size(); i++) {
                    final String skillRequired = String.valueOf(CheckoutActivity.serviceList.get(i).getSkillRequired());
                    String appointmentDate = CheckoutActivity.serviceList.get(i).getAppointmentDate();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dateInString = appointmentDate;

                    try {

                        Date date = formatter.parse(dateInString);

                        appointmentDate = formatter.format(date).toString();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    appointmentDate = appointmentDate.substring(0, 2) + appointmentDate.substring(3, 5) + appointmentDate.substring(6, 10);
                    final String appointmentStore = CheckoutActivity.serviceList.get(i).getAppointmentStore();
                    double appointmentStart = CheckoutActivity.serviceList.get(i).getAppointmentStart();
                    double appointmentEnd = CheckoutActivity.serviceList.get(i).getAppointmentEnd();
                    System.out.println("product" + CheckoutActivity.serviceList.get(i).getSessionsRequired());
                    final ArrayList<String> sessionsList = new ArrayList<String>();
                    for (double j = appointmentStart; j < appointmentEnd; j = j + 0.25) {

                        sessionsList.add(String.valueOf(j).replace(".", "-"));
                    }
                    final int counter = i;
                    final String date = appointmentDate;
                    final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference sessionsRef = mDatabase.getReference("stores").child("storeSchedules").child(appointmentStore).child("appointmentSchedules").child(appointmentDate);
                    System.out.println(appointmentDate);
                    sessionsRef.addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ArrayList<Integer> bookedAppointments = new ArrayList<Integer>();
                                    // Get user value
                                    boolean allAppointmentsValid = true;
                                    if (dataSnapshot.getChildrenCount() == 0) {
                                        for (int i = 0; i < sessionsList.size(); i++) {
                                            bookedAppointments.add(0);
                                        }

                                    } else {
                                        for (int x = 0; x < sessionsList.size(); x++) {
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            int skillsAvailable = preferences.getInt("skillsAvailable", 0);

                                            if (dataSnapshot.child(sessionsList.get(x)).child(skillRequired).getValue() != null) {
                                                bookedAppointments.add(Integer.valueOf(dataSnapshot.child(sessionsList.get(x)).child(skillRequired).getValue().toString()));
                                                if (skillsAvailable <= Integer.valueOf(dataSnapshot.child(sessionsList.get(x)).child(skillRequired).getValue().toString())) {
                                                    CheckoutActivity.serviceList.get(counter).setAppointmentStatus(false);
                                                    System.out.println(skillsAvailable + " " + Integer.valueOf(dataSnapshot.child(sessionsList.get(x)).child(skillRequired).getValue().toString()));
                                                    allAppointmentsValid = false;
                                                    break;
                                                }
                                            } else {

                                                bookedAppointments.add(0);
                                            }
                                        }
                                    }

                                    if (!allAppointmentsValid) {
                                        setResult(RESULT_OK, getIntent());
                                        finish();


                                    } else {
                                        DatabaseReference sessionUpdateRef = mDatabase.getReference("stores").child("storeSchedules").child(appointmentStore).child("appointmentSchedules").child(date);
                                        for (int z = 0; z < sessionsList.size(); z++) {

                                            sessionUpdateRef.child(sessionsList.get(z).toString()).child(skillRequired).setValue((bookedAppointments.get(z) + 1));
                                        }
                                        Toast.makeText(getApplicationContext(), "Transaction Complete",
                                                Toast.LENGTH_LONG).show();
                                        HomeScreenActivity.cartObjects.clear();
                                        Intent intent = new Intent(PaymentActivity.this, HomeScreenActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("Google is playing games:/");
                                }
                            });
                }


            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productsTotal = Double.valueOf(bundle.getFloat("productsTotal"));
            servicesTotal = Double.valueOf(bundle.getFloat("servicesTotal"));
            taxTotal = Double.valueOf(bundle.getFloat("taxTotal"));
        }

        if (productsTotal == 0.0) {
            lytShippingAddress.setVisibility(View.GONE);
        }

        tvPaymentDetails.setText("Payment of: R" + (productsTotal + servicesTotal + taxTotal) + " will be done here");


    }
}
