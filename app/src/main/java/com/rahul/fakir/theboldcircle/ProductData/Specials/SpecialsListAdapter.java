package com.rahul.fakir.theboldcircle.ProductData.Specials;

import android.content.Context;
import android.content.Intent;
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
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSpecialName, tvSpecialDescription;
        public ImageView imgvSpecialType, imgvSpecialForward;

        public MyViewHolder(View view) {
            super(view);
            tvSpecialName = (TextView) view.findViewById(R.id.tvSpecialName);
            tvSpecialDescription = (TextView) view.findViewById(R.id.tvSpecialDescription);
            imgvSpecialType = (ImageView) view.findViewById(R.id.imgvSpecialType);
            imgvSpecialForward = (ImageView) view.findViewById(R.id.imgSpecialForward);
        }
    }


    public SpecialsListAdapter(List<SpecialObject> specialsList, Context context) {
        this.context = context;
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
                    HomeScreenActivity.cartObjects.remove(specialObject.getProductSKU());

                } else {
                    holder.imgvSpecialType.setImageResource(R.mipmap.selected_product_icon);
                    specialObject.getProduct().setSelectedStatus(true);
                    HomeScreenActivity.cartObjects.put(specialObject.getProductSKU(), specialObject.getProduct());

                }

            }
        });

        holder.imgvSpecialForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check this special out!");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, specialObject.getDescription());
                intent.putExtra(Intent.EXTRA_TITLE, specialObject.getName());
                context.startActivity(Intent.createChooser(intent, "Share this special"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return specialsList.size();
    }
}