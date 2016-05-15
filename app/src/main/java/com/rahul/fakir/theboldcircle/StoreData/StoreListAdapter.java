package com.rahul.fakir.theboldcircle.StoreData;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul.fakir.theboldcircle.R;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.MyViewHolder> {

    private List<StoreObject> storeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAddressLocation, tvID;

        public MyViewHolder(View view) {
            super(view);
            tvID = (TextView) view.findViewById(R.id.tvStoreID);
            tvAddressLocation = (TextView) view.findViewById(R.id.tvAddressLocation);

        }
    }


    public StoreListAdapter(List<StoreObject> storeList) {
        this.storeList = storeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StoreObject storeObjects = storeList.get(position);
        holder.tvID.setText(storeObjects.getID());
        holder.tvAddressLocation.setText(storeObjects.addressLocation());

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
}