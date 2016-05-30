package com.rahul.fakir.theboldcircle.ProductData.Specials;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahul.fakir.theboldcircle.Graphics.DividerItemDecoration;
import com.rahul.fakir.theboldcircle.ProductData.Products.ProductObject;
import com.rahul.fakir.theboldcircle.R;

import java.util.ArrayList;
import java.util.List;

public class SpecialsActivity  extends AppCompatActivity {

    private List<SpecialObject> specialsList = new ArrayList<>();
        private RecyclerView recyclerView;
        private SpecialsListAdapter sAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_specials);



            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


            sAdapter = new SpecialsListAdapter(specialsList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(sAdapter);
            prepareSpecialData();
        }

        private void prepareSpecialData() {
            findViewById(R.id.lytLoadingPanel).setVisibility(View.VISIBLE);
        System.out.println("Reached Here");

            final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference specialsRef = mDatabase.getReference("specials");

            specialsRef.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get user value


                            String id;
                            for (DataSnapshot postSnapshotSpecials: dataSnapshot.getChildren()) {
                                id = postSnapshotSpecials.getKey().toString();
                                final SpecialObject special = postSnapshotSpecials.getValue(SpecialObject.class);
                                special.setID(id);
                                DatabaseReference productsRef = mDatabase.getReference("products");
                                productsRef.child("specials").child(special.getProductSKU()).addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                // Get user value
                                                ProductObject product = dataSnapshot.getValue(ProductObject.class);
                                                product.setCategory("specials");
                                                product.setSku(dataSnapshot.getKey().toString());
                                                    special.setProduct(product);

                                                specialsList.add(special);


                                                /*for (DataSnapshot postSnapshotProducts: dataSnapshot.getChildren()) {

                                                    ProductObject product = postSnapshotProducts.getValue(ProductObject.class);
                                                    product.setCategory("specials");
                                                    product.setSku(postSnapshotProducts.getKey().toString());
                                                }*/
                                                sAdapter.notifyDataSetChanged();
                                                findViewById(R.id.lytLoadingPanel).setVisibility(View.GONE);
                                               }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }

                                        });

                            }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("error");
                        }
                    });

        }

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}

public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private SpecialsActivity.ClickListener clickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SpecialsActivity.ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


}

    @Override
    public void onResume() {
        super.onResume();
        sAdapter.notifyDataSetChanged();
    }

}