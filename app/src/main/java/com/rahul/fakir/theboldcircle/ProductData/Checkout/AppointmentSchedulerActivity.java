package com.rahul.fakir.theboldcircle.ProductData.Checkout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.fakir.theboldcircle.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AppointmentSchedulerActivity extends AppCompatActivity {


    private Button btnChangeDate;
    private TextView tvAppointmentDate;
    private int year;
    private int month;
    private int day;
    private HashMap<Double, Integer> availableAppointments;
    private List<TimeObject> appointmentTimesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppointmentListAdapter mAdapter;

    static final int DATE_DIALOG_ID = 999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_scheduler);

        tvAppointmentDate = (TextView) findViewById(R.id.tvAppointmentDate);
        setCurrentDateOnView();
        addListenerOnButton();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new AppointmentListAdapter(appointmentTimesList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }

    // display current date
    public void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //initialize

    }

    public void addListenerOnButton() {
        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            //get date here
            tvAppointmentDate.setText("Appointments on: " + day + "/" + month + "/" + year + ":");
            String input_date = day + "/" + (month + 1) + "/" + year;

            final Date date;
            final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = format.parse(input_date);
                format.applyPattern("EEEE");
                final String dayOfWeek = format.format(date).toLowerCase();
                format.applyPattern("EEEE d MMM");
                final String appointmentDate = format.format(date);
                tvAppointmentDate.setText("Appointments on " + appointmentDate);

                System.out.println(dayOfWeek);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String appointmentStore = preferences.getString("appointmentStore", "");
                final int skillRequired = preferences.getInt("skillRequired", -1);
                final SharedPreferences.Editor editor = preferences.edit();
                editor.putString("appointmentDate", day + "/" + (month + 1) + "/" + year);
                editor.apply();
                //check if store is open on requested date
                final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference operationalRef = mDatabase.getReference("stores").child("storeSchedules").
                        child(appointmentStore).child("operationalHours").child(dayOfWeek);


                operationalRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                availableAppointments = new HashMap();
                                // Get user value
                                if (!((Boolean) dataSnapshot.child("status").getValue())) {
                                    Toast.makeText(getApplicationContext(), "This store is closed on " + appointmentDate,
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    final Double open = Double.valueOf(dataSnapshot.child("open").getValue().toString());
                                    final Double close = Double.valueOf(dataSnapshot.child("close").getValue().toString());

                                    //check if skill offered on that day
                                    DatabaseReference operationalRef = mDatabase.getReference("stores").child("skillsOffered").
                                            child(appointmentStore).child(dayOfWeek).child(String.valueOf(skillRequired));


                                    operationalRef.addListenerForSingleValueEvent(
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    // Get user value
                                                    if (!((Boolean) dataSnapshot.child("status").getValue())) {
                                                        Toast.makeText(getApplicationContext(), "This service is not offered on " + appointmentDate,
                                                                Toast.LENGTH_LONG).show();
                                                    } else {
                                                        final Integer skillsAvailable = Integer.valueOf(dataSnapshot.child("available").getValue().toString());
                                                        editor.putInt("skillsAvailable", skillsAvailable);
                                                        editor.apply();

                                                        if (skillsAvailable < skillRequired) {
                                                            Toast.makeText(getApplicationContext(), "This service is not offered on " + appointmentDate,
                                                                    Toast.LENGTH_LONG).show();
                                                        } else {

                                                            //HARDCODED
                                                            final Double sessionDuration = 0.25;
                                                            final int numberOfSessions = calculateNumberOfSessions(open, close, sessionDuration);

                                                            for (int i = 0; i < numberOfSessions; i++) {
                                                                availableAppointments.put(open + i * sessionDuration, skillsAvailable);

                                                            }


                                                            format.applyPattern("ddMMYYYY");
                                                            final String scheduleID = format.format(date);

                                                            //check if skill offered on that day
                                                            final DatabaseReference availableAppointmentsRef = mDatabase.getReference("stores").child("storeSchedules").
                                                                    child(appointmentStore).child("appointmentSchedules").child(scheduleID);


                                                            availableAppointmentsRef.addListenerForSingleValueEvent(
                                                                    new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            // Get user value
                                                                            if (dataSnapshot.getChildrenCount() == 0) {

                                                                                determineAvailableAppointments(open, close, numberOfSessions, sessionDuration);

                                                                            } else {
                                                                                //get existing appointments
                                                                                for (DataSnapshot postSnapshotSessions : dataSnapshot.getChildren()) {
                                                                                    Double timeSlot = Double.valueOf((postSnapshotSessions.getKey().toString()).replace("-", "."));

                                                                                    if (postSnapshotSessions.child(String.valueOf(skillRequired)).getValue() != null) {
                                                                                        availableAppointments.put(timeSlot, availableAppointments.get(timeSlot) - Integer.valueOf(postSnapshotSessions.child(String.valueOf(skillRequired)).getValue().toString()));

                                                                                    }
                                                                                }
                                                                                determineAvailableAppointments(open, close, numberOfSessions, sessionDuration);

                                                                            }


                                                                        }

                                                                        @Override
                                                                        public void onCancelled(DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };


    public int calculateNumberOfSessions(Double open, Double close, Double sessionDuration) {
        return (int) ((close - open) / sessionDuration);
    }

    public void determineAvailableAppointments(double open, double close, int numberOfSessions, double sessionDuration) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int sessionsRequired = preferences.getInt("sessionsRequired", 0);


        List<Double> possibleAppointments = new ArrayList<Double>();
        Boolean bookingPossible = false;
        for (double i = open; i < close - sessionsRequired * sessionDuration + sessionDuration; i = i + sessionDuration) {
            for (double j = i; j < i + sessionsRequired * sessionDuration; j = j + sessionDuration) {
                if (availableAppointments.get(j) <= 0) {
                    bookingPossible = false;
                    break;
                } else {
                    bookingPossible = true;
                }


            }
            if (bookingPossible) {
                possibleAppointments.add(i);
            }

        }

        appointmentTimesList.clear();
        for (int x = 0; x < possibleAppointments.size(); x++) {
            TimeObject appointmentTime = new TimeObject(possibleAppointments.get(x), possibleAppointments.get(x) + sessionDuration * sessionsRequired);
            appointmentTimesList.add(appointmentTime);
        }/*
        for (Map.Entry<Double, Integer> entry : availableAppointments.entrySet()) {
            if (possibleAppointments.contains(entry.getKey())){
                //System.out.println("AVAILABLE: " + entry.getKey() + " - " + entry.getValue());
                TimeObject appointmentTime = new TimeObject(entry.getKey());
                appointmentTimesList.add(appointmentTime);
            } else {
           //     System.out.println("SHEM: " + entry.getKey() + " - " + entry.getValue());
            }

        }*/


        mAdapter.notifyDataSetChanged();
        final int skillRequired = preferences.getInt("skillRequired", -1);



    }
}