package com.rahul.fakir.theboldcircle.ProductData;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.rahul.fakir.theboldcircle.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private List<tblProducts> productList = new ArrayList<>();
    List<tblCategories> categoriesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductListAdapter pAdapter;
    TextView tvAllCategories, tvCategory1, tvCategory2, tvCategory3, tvCategory4, tvExpandCategories,
    tvDivider0, tvDivider1, tvDivider2, tvDivider3, currentCategory;
    View llytCategoriesSecondLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Backendless.initApp(ProductsActivity.this, "1D58A6DB-C412-6AA4-FFBB-2E2A7EC0CB00", "CDBE2EF7-DC1E-0D16-FFD8-18D0DF281000", "v1");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
                loadProducts("");
                updateCategoryHeadingColor(tvAllCategories);
            }
        });

        tvCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadProducts(tvCategory1.getText().toString());
                updateCategoryHeadingColor(tvCategory1);
            }
        });

        tvCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts(tvCategory2.getText().toString());
                updateCategoryHeadingColor(tvCategory2);
            }
        });

        tvCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts(tvCategory3.getText().toString());
                updateCategoryHeadingColor(tvCategory3);
            }
        });

        tvCategory4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts(tvCategory4.getText().toString());
                updateCategoryHeadingColor(tvCategory4);
            }
        });


        pAdapter = new ProductListAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);
       loadProducts("");
        setCategoryHeaders();
    }

    private void setCategoryHeaders() {
        QueryOptions qOptions = new QueryOptions(5,0);
        BackendlessDataQuery query = new BackendlessDataQuery(qOptions);
        Backendless.Persistence.of(tblCategories.class).find(query, new AsyncCallback<BackendlessCollection<tblCategories>>() {

            @Override
            public void handleResponse(BackendlessCollection<tblCategories> foundCategories) {
                // all Contact instances have been found
                Iterator<tblCategories> iterator = foundCategories.getCurrentPage().iterator();

                while (iterator.hasNext()) {
                    tblCategories categories = iterator.next();
                    tblCategories category = new tblCategories();
                    category.setCategory(categories.getCategory());
                    category.setDescription(categories.getDescription());
                    categoriesList.add(category);
                }
               // pAdapter.notifyDataSetChanged();
                tvCategory1.setText(toDisplayCase(categoriesList.get(0).getCategory()));
                switch (categoriesList.size()) {
                    case 2: {
                        tvDivider1.setVisibility(View.VISIBLE);
                        tvCategory2.setVisibility(View.VISIBLE);
                        tvCategory2.setText(toDisplayCase(categoriesList.get(1).getCategory()));
                        break;
                    }
                    case 3: {
                        tvDivider1.setVisibility(View.VISIBLE);
                        tvCategory2.setVisibility(View.VISIBLE);
                      tvDivider2.setVisibility(View.VISIBLE);
                        tvCategory3.setVisibility(View.VISIBLE);
                        tvCategory2.setText(toDisplayCase(categoriesList.get(1).getCategory()));
                        tvCategory3.setText(toDisplayCase(categoriesList.get(2).getCategory()));
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
                        tvCategory2.setText(toDisplayCase(categoriesList.get(1).getCategory()));
                        tvCategory3.setText(toDisplayCase(categoriesList.get(2).getCategory()));
                        tvCategory4.setText(toDisplayCase(categoriesList.get(3).getCategory()));
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
                        tvCategory2.setText(toDisplayCase(categoriesList.get(1).getCategory()));
                        tvCategory3.setText(toDisplayCase(categoriesList.get(2).getCategory()));
                        tvCategory4.setText(toDisplayCase(categoriesList.get(3).getCategory()));
                        break;
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                System.out.println("error with retrieval");
            }
        });

     /*   tblProducts product = new tblProducts("001", "Product 1", "Description of product 1", "product", "1.87", "");
        productList.add(product);

        product = new tblProducts("002", "Product 2", "Description of product 2", "product", "45.76", "");
        productList.add(product);

        product = new tblProducts("003", "Product 3", "Description of product 3", "product", "3.83", "");
        productList.add(product);

        product = new tblProducts("004", "Product 4", "Description of product 4", "product", "999.64", "");
        productList.add(product);*/


    }

    public void loadProducts(String category){
        findViewById(R.id.productsLoadingPanel).setVisibility(View.VISIBLE);
        productList.clear();
        pAdapter.notifyDataSetChanged();
        System.out.println("INTERFACE CHANGE");
        String whereClause = "";
        if (!category.equals("")) {
            whereClause = "category = '" + category + "'";
        }
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        Backendless.Persistence.of( tblProducts.class).find(dataQuery, new AsyncCallback<BackendlessCollection<tblProducts>>(){
            @Override
            public void handleResponse( BackendlessCollection<tblProducts> foundProducts )
            {
                // all Contact instances have been found
                Iterator<tblProducts> iterator = foundProducts.getCurrentPage().iterator();

                while (iterator.hasNext()) {
                    tblProducts retrievedProducts = iterator.next();
                    tblProducts product = new tblProducts(retrievedProducts.getSku(), retrievedProducts.getName(), retrievedProducts.getDescription(), retrievedProducts.getType(), retrievedProducts.getPrice(), retrievedProducts.getCategory());
                    productList.add(product);


                }
                pAdapter.notifyDataSetChanged();
                findViewById(R.id.productsLoadingPanel).setVisibility(View.GONE);
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                findViewById(R.id.productsLoadingPanel).setVisibility(View.GONE);
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

    public static String toDisplayCase(String s) {

        final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
        // to be capitalized

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
        }
        return sb.toString();
    }
}