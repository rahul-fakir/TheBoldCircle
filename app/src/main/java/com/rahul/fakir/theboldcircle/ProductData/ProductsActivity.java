package com.rahul.fakir.theboldcircle.ProductData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rahul.fakir.theboldcircle.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private List<ProductObject> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductListAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pAdapter = new ProductListAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        ProductObject product = new ProductObject("001", "Product 1", "Description of product 1", "product", 1.87);
        productList.add(product);

        product = new ProductObject("002", "Product 2", "Description of product 2", "product", 45.76);
        productList.add(product);

        product = new ProductObject("003", "Product 3", "Description of product 3", "product", 3.83);
        productList.add(product);

        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("002", "Product 2", "Description of product 2", "product", 45.76);
        productList.add(product);

        product = new ProductObject("003", "Product 3", "Description of product 3", "product", 3.83);
        productList.add(product);

        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("002", "Product 2", "Description of product 2", "product", 45.76);
        productList.add(product);

        product = new ProductObject("003", "Product 3", "Description of product 3", "product", 3.83);
        productList.add(product);

        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("002", "Product 2", "Description of product 2", "product", 45.76);
        productList.add(product);

        product = new ProductObject("003", "Product 3", "Description of product 3", "product", 3.83);
        productList.add(product);

        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("002", "Product 2", "Description of product 2", "product", 45.76);
        productList.add(product);

        product = new ProductObject("003", "Product 3", "Description of product 3", "product", 3.83);
        productList.add(product);

        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("002", "Product 2", "Description of product 2", "product", 45.76);
        productList.add(product);

        product = new ProductObject("003", "Product 3", "Description of product 3", "product", 3.83);
        productList.add(product);

        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);
        product = new ProductObject("004", "Product 4", "Description of product 4", "product", 999.64);
        productList.add(product);


        pAdapter.notifyDataSetChanged();

    }
}
