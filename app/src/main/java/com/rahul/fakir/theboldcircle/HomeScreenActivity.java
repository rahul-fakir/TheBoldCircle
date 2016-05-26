package com.rahul.fakir.theboldcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rahul.fakir.theboldcircle.ProductData.Checkout.AppointmentSchedulerActivity;
import com.rahul.fakir.theboldcircle.ProductData.Products.ProductObject;
import com.rahul.fakir.theboldcircle.ProductData.Products.ProductsActivity;
import com.rahul.fakir.theboldcircle.ProductData.Specials.SpecialsActivity;
import com.rahul.fakir.theboldcircle.StoreData.StoreLocationActivity;
import com.rahul.fakir.theboldcircle.StoreData.StoresActivity;
import com.rahul.fakir.theboldcircle.UserData.LogInActivity;
import com.rahul.fakir.theboldcircle.UserData.UserProfileActivity;

import java.util.HashMap;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




    public static HashMap<String, ProductObject> cartObjects = new HashMap();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CardView cvStore = (CardView) findViewById(R.id.cvStore);
        cvStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (HomeScreenActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });


        CardView cvContact = (CardView) findViewById(R.id.cvContact);
        cvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (HomeScreenActivity.this, StoresActivity.class);
                intent.putExtra("listType", 0);
                startActivity(intent);
            }
        });


        CardView cvSpecials = (CardView) findViewById(R.id.cvSpecials);
        cvSpecials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (HomeScreenActivity.this, SpecialsActivity.class);
                startActivity(intent);
            }
        });

        CardView cvFindNearestStore = (CardView) findViewById(R.id.cvFindNearestStore);
        cvFindNearestStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intenet = new Intent(HomeScreenActivity.this, StoreLocationActivity.class);
                Intent intent = new Intent(HomeScreenActivity.this, AppointmentSchedulerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(HomeScreenActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rewards) {

        } else if (id == R.id.nav_services_products) {

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_license) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {

                // user has been logged out.
                FirebaseAuth.getInstance().signOut();
                Intent intentToCreateProfile = new Intent(HomeScreenActivity.this, LogInActivity.class);
                intentToCreateProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentToCreateProfile);
                finish();




    }



}
