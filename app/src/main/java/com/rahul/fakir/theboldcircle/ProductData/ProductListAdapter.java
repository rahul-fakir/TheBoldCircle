package com.rahul.fakir.theboldcircle.ProductData;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul.fakir.theboldcircle.R;

import java.util.List;

/**
 * Created by rahul.fakir on 2016/05/13.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private List<ProductObject> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, type, description, price;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvName);
            type = (TextView) view.findViewById(R.id.tvType);
            description = (TextView) view.findViewById(R.id.tvDescription);
            price = (TextView) view.findViewById(R.id.tvPrice);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductObject product = productList.get(position);
        holder.name.setText(product.getName());
        holder.type.setText(product.getType());
        holder.description.setText(product.getDescription());
        holder.price.setText("R" + String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}