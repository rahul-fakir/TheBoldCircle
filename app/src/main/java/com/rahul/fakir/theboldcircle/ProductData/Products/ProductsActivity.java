package com.rahul.fakir.theboldcircle.ProductData.Products;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rahul.fakir.theboldcircle.ProductData.Categories.AllCategoriesActivity;
import com.rahul.fakir.theboldcircle.ProductData.Categories.CategoryObject;
import com.rahul.fakir.theboldcircle.ProductData.Checkout.CheckoutActivity;
import com.rahul.fakir.theboldcircle.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private int REQUEST_CODE = 1;
    private List<ProductObject> productList = new ArrayList<>();
    List<CategoryObject> categoriesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductListAdapter pAdapter;
    public static TextView tvAllCategories, tvCategory1, tvCategory2, tvCategory3, tvCategory4, tvExpandCategories,
            tvDivider0, tvDivider1, tvDivider2, tvDivider3, currentCategory, tvSubmitProducts;
    View llytCategoriesSecondLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvSubmitProducts = (TextView) findViewById(R.id.tvSubmitProducts);
        tvAllCategories = (TextView) findViewById(R.id.tvAllCategories);
        tvCategory1 = (TextView) findViewById(R.id.tvCategory1);
        tvCategory2 = (TextView) findViewById(R.id.tvCategory2);
        tvCategory3 = (TextView) findViewById(R.id.tvCategory3);
        tvCategory4 = (TextView) findViewById(R.id.tvCategory4);
        tvDivider0 = (TextView) findViewById(R.id.tvDivider0);
        tvDivider1 = (TextView) findViewById(R.id.tvDivider1);
        tvDivider2 = (TextView) findViewById(R.id.tvDivider2);
        tvDivider3 = (TextView) findViewById(R.id.tvDivider3);
        tvExpandCategories = (TextView) findViewById(R.id.tvExpandCategories);
        llytCategoriesSecondLevel = (View) findViewById(R.id.llytCategoriesSecondLevel);

        currentCategory = tvAllCategories;


        tvAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts();
                updateCategoryHeadingColor(tvAllCategories);
            }
        });

        tvCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadProductsByCategory(tvCategory1.getText().toString());
                updateCategoryHeadingColor(tvCategory1);
            }
        });

        tvCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProductsByCategory(tvCategory2.getText().toString());
                updateCategoryHeadingColor(tvCategory2);
            }
        });

        tvCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProductsByCategory(tvCategory3.getText().toString());
                updateCategoryHeadingColor(tvCategory3);
            }
        });

        tvCategory4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProductsByCategory(tvCategory4.getText().toString());
                updateCategoryHeadingColor(tvCategory4);
            }
        });

        tvExpandCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, AllCategoriesActivity.class);

                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        tvSubmitProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ProductsActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });


        pAdapter = new ProductListAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);
        loadProducts();
        setCategoryHeaders();
    }

    private void setCategoryHeaders() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference categoryRef = mDatabase.getReference("categoryHeaders");


        categoryRef.limitToFirst(5).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                            CategoryObject category = new CategoryObject();
                            category.setCategory(postSnapshot.getKey().toString());
                            categoriesList.add(category);

                        }
                        tvCategory1.setText(categoriesList.get(0).getCategory());
                        switch (categoriesList.size()) {
                            case 2: {
                                tvDivider1.setVisibility(View.VISIBLE);
                                tvCategory2.setVisibility(View.VISIBLE);
                                tvCategory2.setText(categoriesList.get(1).getCategory());
                                break;
                            }
                            case 3: {
                                tvDivider1.setVisibility(View.VISIBLE);
                                tvCategory2.setVisibility(View.VISIBLE);
                                tvDivider2.setVisibility(View.VISIBLE);
                                tvCategory3.setVisibility(View.VISIBLE);
                                tvCategory2.setText(categoriesList.get(1).getCategory());
                                tvCategory3.setText(categoriesList.get(2).getCategory());
                                break;
                            }
                            case 4: {
                                tvDivider1.setVisibility(View.VISIBLE);
                                tvCategory2.setVisibility(View.VISIBLE);
                                tvDivider1.setVisibility(View.VISIBLE);
                                tvCategory3.setVisibility(View.VISIBLE);
                                llytCategoriesSecondLevel.setVisibility(View.VISIBLE);
                                tvDivider2.setVisibility(View.VISIBLE);
                                tvCategory4.setVisibility(View.VISIBLE);
                                tvCategory2.setText(categoriesList.get(1).getCategory());
                                tvCategory3.setText(categoriesList.get(2).getCategory());
                                tvCategory4.setText(categoriesList.get(3).getCategory());
                                break;
                            }
                            case 5: {
                                tvDivider1.setVisibility(View.VISIBLE);
                                tvCategory2.setVisibility(View.VISIBLE);
                                tvDivider2.setVisibility(View.VISIBLE);
                                tvCategory3.setVisibility(View.VISIBLE);
                                llytCategoriesSecondLevel.setVisibility(View.VISIBLE);
                                tvDivider3.setVisibility(View.VISIBLE);
                                tvCategory4.setVisibility(View.VISIBLE);
                                tvExpandCategories.setVisibility(View.VISIBLE);
                                tvCategory2.setText(categoriesList.get(1).getCategory());
                                tvCategory3.setText(categoriesList.get(2).getCategory());
                                tvCategory4.setText(categoriesList.get(3).getCategory());
                                break;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




    }






    public void loadProducts() {
        findViewById(R.id.productsLoadingPanel).setVisibility(View.VISIBLE);
        productList.clear();
        pAdapter.notifyDataSetChanged();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference productsRef = mDatabase.getReference("products");

        productsRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value


                        String category;
                        for (DataSnapshot postSnapshotCategories: dataSnapshot.getChildren()) {
                            category = postSnapshotCategories.getKey().toString();
                            for (DataSnapshot postSnapshotProducts: postSnapshotCategories.getChildren()) {
                                ProductObject product = postSnapshotProducts.getValue(ProductObject.class);
                                product.setCategory(category);
                                 product.setSku(postSnapshotProducts.getKey().toString());
                                   productList.add(product);
                            }





                        }
                        pAdapter.notifyDataSetChanged();
                        findViewById(R.id.productsLoadingPanel).setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void loadProductsByCategory(final String category) {
        findViewById(R.id.productsLoadingPanel).setVisibility(View.VISIBLE);
        productList.clear();
        pAdapter.notifyDataSetChanged();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference productsRef = mDatabase.getReference("products");

        productsRef.child(category).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                        for (DataSnapshot postSnapshotProducts: dataSnapshot.getChildren()) {

                                ProductObject product = postSnapshotProducts.getValue(ProductObject.class);
                                product.setCategory(category);
                                product.setSku(postSnapshotProducts.getKey().toString());
                                productList.add(product);

                        }
                        pAdapter.notifyDataSetChanged();
                        findViewById(R.id.productsLoadingPanel).setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
System.out.println("Google is playing games:/");
                    }
                });
    }

    public void updateCategoryHeadingColor(TextView newCategory) {
        currentCategory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mainText));
        currentCategory.setTypeface(null, Typeface.NORMAL);
        currentCategory = newCategory;
        currentCategory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.highlightedText));
        currentCategory.setTypeface(null, Typeface.BOLD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("categoryName")) {
                String temp = "";

                if (tvCategory2.getText().equals(data.getExtras().getString("categoryName"))) {
                    temp = tvCategory2.getText().toString();
                    tvCategory2.setText(tvCategory1.getText().toString());
                    tvCategory1.setText(temp);

                } else
                if (tvCategory3.getText().equals(data.getExtras().getString("categoryName"))) {
                    temp = tvCategory3.getText().toString();
                    tvCategory3.setText(tvCategory1.getText().toString());
                    tvCategory1.setText(temp);

                } else
                if (tvCategory4.getText().equals(data.getExtras().getString("categoryName"))) {
                    temp = tvCategory4.getText().toString();
                    tvCategory4.setText(tvCategory1.getText().toString());
                    tvCategory1.setText(temp);

                } else if (!(tvCategory1.getText().equals(data.getExtras().getString("categoryName")))) {
                    tvCategory4.setText(ProductsActivity.tvCategory3.getText());
                    tvCategory3.setText(ProductsActivity.tvCategory2.getText());
                    tvCategory2.setText(ProductsActivity.tvCategory1.getText());
                    tvCategory1.setText(data.getExtras().getString("categoryName"));

                }
                updateCategoryHeadingColor(tvCategory1);
                loadProductsByCategory(tvCategory1.getText().toString());
            }
        }
    }




}