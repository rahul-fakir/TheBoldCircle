package com.rahul.fakir.theboldcircle.ProductData.Checkout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rahul.fakir.theboldcircle.Graphics.DividerItemDecoration;
import com.rahul.fakir.theboldcircle.HomeScreenActivity;
import com.rahul.fakir.theboldcircle.ProductData.Products.ProductObject;
import com.rahul.fakir.theboldcircle.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    private Double productsTotal = 0.0, servicesTotal = 0.0, taxTotal = 0.0;
    public static List<ProductObject> productList = new ArrayList<>();
    public static List<ProductObject> serviceList = new ArrayList<>();
    private RecyclerView productsRecyclerView, serviceRecyclerView;
    private ProductCheckoutListAdapter productsAdapter, serviceAdapter;
    private TextView tvProductsTotal, tvServicesTotal, tvSubTotal, tvTaxTotal, tvTotal;
    private Button btnCompletePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvProductsTotal = (TextView) findViewById(R.id.tvProductsTotal);
        tvServicesTotal = (TextView) findViewById(R.id.tvServicesTotal);
        tvSubTotal = (TextView) findViewById(R.id.tvSubTotal);
        tvTaxTotal = (TextView) findViewById(R.id.tvTaxTotal);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        btnCompletePayment = (Button) findViewById(R.id.btnCompletePayment);
        btnCompletePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                intent.putExtra("productsTotal", Float.valueOf(String.valueOf(productsTotal)));
                intent.putExtra("servicesTotal", Float.valueOf(String.valueOf(servicesTotal)));
                intent.putExtra("taxTotal", Float.valueOf(String.valueOf(taxTotal)));

                startActivityForResult(intent, 4);


            }
        });

//products
        productsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        productsAdapter = new ProductCheckoutListAdapter(productList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        productsRecyclerView.setLayoutManager(mLayoutManager);
        productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productsRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        productsRecyclerView.setAdapter(productsAdapter);
        productsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), productsRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ProductObject product = productList.get(position);
           }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        serviceRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_services);
        serviceAdapter = new ProductCheckoutListAdapter(serviceList, this);
        RecyclerView.LayoutManager serviceLayoutManager = new LinearLayoutManager(getApplicationContext());
        serviceRecyclerView.setLayoutManager(serviceLayoutManager);
        serviceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        serviceRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        serviceRecyclerView.setAdapter(serviceAdapter);
        serviceRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), serviceRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ProductObject product = serviceList.get(position);
                Toast.makeText(getApplicationContext(), product.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareProductData();
    }

    private void prepareProductData() {
        productList.clear();
        serviceList.clear();
        for(Map.Entry<String, ProductObject> entry : HomeScreenActivity.cartObjects.entrySet()) {

            if (entry.getValue().getType().equals("good")) {
                productsTotal += Double.parseDouble(entry.getValue().getPrice().substring(1));
                productList.add(entry.getValue());
            } else {
                serviceList.add(entry.getValue());
                servicesTotal += Double.parseDouble(entry.getValue().getPrice().substring(1));
            }

            // do what you have to do here
            // In your case, an other loop.
        }
        DecimalFormat df2DecPlaces = new DecimalFormat("########.##");
        tvProductsTotal.setText("R" + df2DecPlaces.format(productsTotal));
        tvServicesTotal.setText("R" + df2DecPlaces.format(servicesTotal));
        tvSubTotal.setText("R" + df2DecPlaces.format(productsTotal + servicesTotal));
        tvTaxTotal.setText("R" + df2DecPlaces.format(taxTotal));
        tvTotal.setText("R" + df2DecPlaces.format(productsTotal + servicesTotal + taxTotal));



        productsAdapter.notifyDataSetChanged();
        serviceAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private CheckoutActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CheckoutActivity.ClickListener clickListener) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       Toast.makeText(this, "HIIIII",
               Toast.LENGTH_LONG).show();

       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
       Boolean storeChosen = preferences.getBoolean("storeChosen", false);
       SharedPreferences.Editor editor = preferences.edit();
       Boolean appointmentSet = preferences.getBoolean("appointmentSet", false);
       Boolean appointmentsNotValid = preferences.getBoolean("appointmentsNotValid", false);
       String appointmentStore = null, currentProduct;
       if (storeChosen) {

           appointmentStore = preferences.getString("appointmentStore", "");
           currentProduct = preferences.getString("currentProduct", "");
            for (int i = 0; i < serviceAdapter.checkoutProductList.size(); i++) {

                if (serviceAdapter.checkoutProductList.get(i).getSku().equals(currentProduct)){
                    serviceAdapter.checkoutProductList.get(i).setName(appointmentStore);
                    serviceAdapter.notifyDataSetChanged();


                    editor.putBoolean("storeChosen", false);
                    editor.apply();
                    Intent intent = new Intent(CheckoutActivity.this, AppointmentSchedulerActivity.class);
                    startActivityForResult(intent, 2);

                }
            }

       } else if (appointmentSet) {
           double appointmentStart = Double.valueOf(preferences.getFloat("appointmentStart", 0));
           double appointmentEnd = Double.valueOf(preferences.getFloat("appointmentEnd", 0));
           String appointmentStoreName = preferences.getString("appointmentStoreName", "");
           appointmentStore = preferences.getString("appointmentStore", "");
           currentProduct = preferences.getString("currentProduct", "");
           String appointmentDate = preferences.getString("appointmentDate", "");
           int skillsAvailable = preferences.getInt("skillsAvailable", 0);

           for (int i = 0; i < serviceAdapter.checkoutProductList.size(); i++) {

               if (serviceAdapter.checkoutProductList.get(i).getSku().equals(currentProduct)) {
                   serviceAdapter.checkoutProductList.get(i).setAppointmentStatus(true);
                   serviceAdapter.checkoutProductList.get(i).setAppointmentStore(appointmentStore);
                   serviceAdapter.checkoutProductList.get(i).setAppointmentStoreName(appointmentStoreName);
                   serviceAdapter.checkoutProductList.get(i).setAppointmentStart(appointmentStart);
                   serviceAdapter.checkoutProductList.get(i).setAppointmentEnd(appointmentEnd);
                   serviceAdapter.checkoutProductList.get(i).setAppointmentDate(appointmentDate);
                   serviceAdapter.checkoutProductList.get(i).setSkillsAvailable(skillsAvailable);

                   serviceAdapter.notifyDataSetChanged();
               }
           }

       } else if (appointmentsNotValid) {
           //Need to adjust for two users booking at the same time;
           serviceAdapter.notifyDataSetChanged();

       }

    }


}