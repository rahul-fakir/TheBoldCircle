package com.rahul.fakir.theboldcircle.ProductData.Products;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.fakir.theboldcircle.HomeScreenActivity;
import com.rahul.fakir.theboldcircle.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rahul.fakir on 2016/05/13.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private List<ProductObject> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, type, description, price;
        public ImageView itemSelect;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvName);
            type = (TextView) view.findViewById(R.id.tvType);
            description = (TextView) view.findViewById(R.id.tvDescription);
            price = (TextView) view.findViewById(R.id.tvPrice);
            itemSelect = (ImageView) view.findViewById(R.id.imgvProductSelected);
        }
    }



    public ProductListAdapter(List<ProductObject> productList) {
        this.productList = productList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProductObject product = productList.get(position);
        holder.name.setText(product.getName());
        holder.type.setText(product.getType());
        holder.description.setText(product.getDescription());
        holder.price.setText(product.getPrice());
        if (HomeScreenActivity.cartObjects.containsKey(product.getSku())){

            product.setSelectedStatus(true);
            holder.itemSelect.setImageResource(R.mipmap.selected_product_icon);
        } else {
            holder.itemSelect.setImageResource(R.mipmap.unselected_product_icon);
        }

        holder.itemSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (product.getSelectedStatus()) {
                    holder.itemSelect.setImageResource(R.mipmap.unselected_product_icon);
                    product.setSelectedStatus(false);
                   // shoppingCartProductID.remove(product.getSku());
                    HomeScreenActivity.cartObjects.remove(product.getSku());

                } else {
                    holder.itemSelect.setImageResource(R.mipmap.selected_product_icon);
                    product.setSelectedStatus(true);
                  //  shoppingCartProductID.add(product.getSku());
                    HomeScreenActivity.cartObjects.put(product.getSku(), product);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}