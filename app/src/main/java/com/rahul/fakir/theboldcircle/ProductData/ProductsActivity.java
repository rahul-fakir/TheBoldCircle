package com.rahul.fakir.theboldcircle.ProductData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.rahul.fakir.theboldcircle.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private List<Products> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductListAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Backendless.initApp( ProductsActivity.this, "1D58A6DB-C412-6AA4-FFBB-2E2A7EC0CB00", "CDBE2EF7-DC1E-0D16-FFD8-18D0DF281000", "v1" );

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pAdapter = new ProductListAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        prepareProductData();
    }

    private void prepareProductData() {


        Backendless.Persistence.of( Products.class).find(new AsyncCallback<BackendlessCollection<Products>>(){
            @Override
            public void handleResponse( BackendlessCollection<Products> foundContacts )
            {
                // all Contact instances have been found
                Iterator<Products> iterator = foundContacts.getCurrentPage().iterator();

                while( iterator.hasNext() )
                {
                    Products person = iterator.next();
                    Products product = new Products(person.getSku(),person.getName(), person.getDescription(), person.getType(), person.getPrice(), person.getCategory());
                    productList.add(product);
                }
                pAdapter.notifyDataSetChanged();
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                System.out.println("error with retrieval");
            }
        });

     /*   Products product = new Products("001", "Product 1", "Description of product 1", "product", "1.87", "");
        productList.add(product);

        product = new Products("002", "Product 2", "Description of product 2", "product", "45.76", "");
        productList.add(product);

        product = new Products("003", "Product 3", "Description of product 3", "product", "3.83", "");
        productList.add(product);

        product = new Products("004", "Product 4", "Description of product 4", "product", "999.64", "");
        productList.add(product);*/




    }
}
