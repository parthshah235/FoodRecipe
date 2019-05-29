package com.example.foodrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class userPannel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView dp;
    TextView tvN, tvM;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;


    protected void onStart()
    {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pannel);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser() == null)
                    {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
            }
        };
        //dp = (ImageView) findViewById(R.id.imageView);
        //tvN = (TextView) findViewById(R.id.name);
        //tvM = (TextView) findViewById(R.id.email);

        //Bundle b = getIntent().getExtras();
        //tvN.setText(b.getString("Name"));
        //tvM.setText(b.getString("Mail"));

        //Glide.with(this).load(b.getString("Photo")).into(dp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Bundle b = getIntent().getExtras();

        //Glide.with(this).load(b.getString("Photo")).into(dp);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvN = (TextView) navigationView.getHeaderView(0).findViewById(R.id.name);
        tvN.setText(b.getString("Name"));
        tvM = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        tvM.setText(b.getString("Mail"));
        dp = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Glide.with(this).load(b.getString("Photo")).into(dp);
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
        getMenuInflater().inflate(R.menu.user_pannel, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.favourite) {

            fragment = new Favourite();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container1, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.Guj) {

            fragment = new guj_cuisine();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container1, fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.Chinese) {

            fragment = new chinese_cuisine();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container1, fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.NorthIndian) {

            fragment = new northIndian_cuisine();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container1, fragment);
            fragmentTransaction.commit();

        }

        else if (id == R.id.SouthIndian) {

            fragment = new southIndian_cuisine();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container1, fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.raj) {

            fragment = new rajasthani_cuisine();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container1, fragment);
            fragmentTransaction.commit();

        }

        else if (id == R.id.SouthIndian) {

            fragment = new otherCuisine();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container1, fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_share) {

            Intent sendIntent=new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Download our App :- www.foodrecipe.com");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,getResources().getText(R.string.send_to)));

        } else if (id == R.id.Logout) {
            auth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
