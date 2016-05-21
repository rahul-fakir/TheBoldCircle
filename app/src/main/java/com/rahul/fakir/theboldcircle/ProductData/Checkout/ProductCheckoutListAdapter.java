package com.rahul.fakir.theboldcircle.ProductData.Checkout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul.fakir.theboldcircle.ProductData.Products.ProductObject;
import com.rahul.fakir.theboldcircle.R;

import java.util.List;

/**
 * Created by rahul.fakir on 2016/05/19.
 */
public class ProductCheckoutListAdapter extends RecyclerView.Adapter<ProductCheckoutListAdapter.MyViewHolder> {

    private List<ProductObject> checkoutProductList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPrice, tvSKU, tvAppointmentDate, tvAppointmentTime, tvAppointmentStore, tvAppointmentReferenceNo;
    public View appointmentView;
        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvSKU = (TextView) view.findViewById(R.id.tvSKU);
            tvAppointmentDate = (TextView) view.findViewById(R.id.tvAppointmentDate);
            tvAppointmentTime = (TextView) view.findViewById(R.id.tvAppointmentTime);
            tvAppointmentStore = (TextView) view.findViewById(R.id.tvAppointmentStore);
            tvAppointmentReferenceNo = (TextView) view.findViewById(R.id.tvAppointmentReferenceNumber);
            appointmentView =  view.findViewById(R.id.rlAppointmentView);
        }
    }


    public ProductCheckoutListAdapter(List<ProductObject> checkoutProductList) {
        this.checkoutProductList = checkoutProductList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_checkout_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductObject product = checkoutProductList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());
        holder.tvSKU.setText(product.getSku());
        if (product.getType().equals("service")) {
            holder.appointmentView.setVisibility(View.VISIBLE);
        }
    }




    @Override
    public int getItemCount() {
        return checkoutProductList.size();
    }
}