package com.rahul.fakir.theboldcircle.ProductData.Categories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul.fakir.theboldcircle.R;

import java.util.List;

/**
 * Created by rahul.fakir on 2016/05/19.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    private List<CategoryObject> categoryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  tvCategoryName;

        public MyViewHolder(View view) {
            super(view);
            tvCategoryName = (TextView) view.findViewById(R.id.tvCategoryName);
        }
    }


    public CategoryListAdapter(List<CategoryObject> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryObject category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getCategory());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
