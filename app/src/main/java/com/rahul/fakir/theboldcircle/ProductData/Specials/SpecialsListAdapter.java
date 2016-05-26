package com.rahul.fakir.theboldcircle.ProductData.Specials;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.fakir.theboldcircle.HomeScreenActivity;
import com.rahul.fakir.theboldcircle.R;

import java.util.List;

/**
 * Created by rahul.fakir on 2016/05/21.
 */
public class SpecialsListAdapter extends RecyclerView.Adapter<SpecialsListAdapter.MyViewHolder> {

    private List<SpecialObject> specialsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSpecialName, tvSpecialDescription;
        public ImageView imgvSpecialType;

        public MyViewHolder(View view) {
            super(view);
            tvSpecialName = (TextView) view.findViewById(R.id.tvSpecialName);
            tvSpecialDescription = (TextView) view.findViewById(R.id.tvSpecialDescription);
            imgvSpecialType = (ImageView) view.findViewById(R.id.imgvSpecialType);

        }
    }


    public SpecialsListAdapter(List<SpecialObject> specialsList) {
        this.specialsList = specialsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specials_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SpecialObject specialObject = specialsList.get(position);
        holder.tvSpecialName.setText(specialObject.getName());
        holder.tvSpecialDescription.setText(specialObject.getDescription());



        if (HomeScreenActivity.cartObjects.containsKey(specialObject.getProductSKU())){

            specialObject.getProduct().setSelectedStatus(true);
            holder.imgvSpecialType.setImageResource(R.mipmap.selected_product_icon);
        } else {
            if (specialObject.getProduct().getType().equals("good")){
            holder.imgvSpecialType.setImageResource(R.mipmap.unselected_product_icon);
            } else
            {
                holder.imgvSpecialType.setImageResource(R.mipmap.calendar_icon);
            }
        }


        holder.imgvSpecialType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (specialObject.getProduct().getSelectedStatus()) {
                    holder.imgvSpecialType.setImageResource(R.mipmap.unselected_product_icon);
                    specialObject.getProduct().setSelectedStatus(false);
                    // shoppingCartProductID.remove(product.getSku());
                    HomeScreenActivity.cartObjects.remove(specialObject.getProductSKU());

                } else {
                    holder.imgvSpecialType.setImageResource(R.mipmap.selected_product_icon);
                    specialObject.getProduct().setSelectedStatus(true);
                    //  shoppingCartProductID.add(product.getSku());
                    HomeScreenActivity.cartObjects.put(specialObject.getProductSKU(), specialObject.getProduct());

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return specialsList.size();
    }
}