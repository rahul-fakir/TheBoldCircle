package com.rahul.fakir.theboldcircle.ProductData.Checkout;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rahul.fakir.theboldcircle.R;

import java.util.List;

/**
 * Created by rahul.fakir on 2016/05/27.
 */
public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {

    private List<TimeObject> timeObjectsList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTime;
        public Button btnSelectAppointment;

        public MyViewHolder(View view) {
            super(view);
            tvTime = (TextView) view.findViewById(R.id.tvAppointmentTime);
            btnSelectAppointment = (Button) view.findViewById(R.id.btnSelectAppointment);

        }
    }


    public AppointmentListAdapter(List<TimeObject> timeObjectsList, Context context) {
        this.timeObjectsList = timeObjectsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_appointments_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final TimeObject appointment = timeObjectsList.get(position);
        holder.tvTime.setText(appointment.getSTime());

        holder.btnSelectAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("appointmentSet", true);
                editor.putFloat("appointmentStart", (float) appointment.getStartTime());
                editor.putFloat("appointmentEnd", (float) appointment.getEndTime());
                editor.apply();
                ((Activity) context).setResult(((Activity) context).RESULT_OK, ((Activity) context).getIntent());

                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return timeObjectsList.size();
    }
}