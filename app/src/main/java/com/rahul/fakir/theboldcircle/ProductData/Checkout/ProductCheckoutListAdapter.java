package com.rahul.fakir.theboldcircle.ProductData.Checkout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rahul.fakir.theboldcircle.ProductData.Products.ProductObject;
import com.rahul.fakir.theboldcircle.R;
import com.rahul.fakir.theboldcircle.StoreData.StoresActivity;

import java.util.List;

/**
 * Created by rahul.fakir on 2016/05/19.
 */
public class ProductCheckoutListAdapter extends RecyclerView.Adapter<ProductCheckoutListAdapter.MyViewHolder> {

    public List<ProductObject> checkoutProductList;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPrice, tvSKU, tvAppointmentDate, tvAppointmentTime, tvAppointmentStore, tvAppointmentReferenceNo;
    public Button btnScheduleAppointment;
        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvSKU = (TextView) view.findViewById(R.id.tvSKU);
            tvAppointmentDate = (TextView) view.findViewById(R.id.tvAppointmentDate);
            tvAppointmentTime = (TextView) view.findViewById(R.id.tvAppointmentTime);
            tvAppointmentStore = (TextView) view.findViewById(R.id.tvAppointmentStore);
            tvAppointmentReferenceNo = (TextView) view.findViewById(R.id.tvAppointmentReferenceNumber);
            btnScheduleAppointment =  (Button) view.findViewById(R.id.btnScheduleAppointment);
        }
    }


    public ProductCheckoutListAdapter(List<ProductObject> checkoutProductList, Context context) {
        this.checkoutProductList = checkoutProductList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_checkout_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProductObject product = checkoutProductList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());
        holder.tvSKU.setText(product.getSku());
        if (product.getType().equals("service")) {
            holder.btnScheduleAppointment.setVisibility(View.VISIBLE);
           holder.btnScheduleAppointment.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( ((Activity)context));
                   SharedPreferences.Editor editor = preferences.edit();
                   editor.putBoolean("storeChosen", false);
                   editor.putString("currentProduct", product.getSku());
                   editor.apply();

                   Intent intent = new Intent(context, StoresActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                   intent.putExtra("listType", 1);
                   context.startActivity(intent);

                   Activity origin = (Activity)context;
                   origin.startActivityForResult(intent, 1);

               }
           });
        }
    }




    @Override
    public int getItemCount() {
        return checkoutProductList.size();

    }


}