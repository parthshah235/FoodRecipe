package com.example.foodrecipe;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chefPannel extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button signout;
    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_pannel);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        reference = FirebaseDatabase.getInstance().getReference("chefDetails");
        auth = FirebaseAuth.getInstance();
        Log.e("HEre",auth.getUid());

        loadFragment(new home_fragment());

    }


    private boolean loadFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

                Bundle b = getIntent().getExtras();
                if(b != null)
                {
                    String id,nm,mail,pass,date,gender;
                    id = auth.getUid();
                    nm = b.getString("name");
                    mail = b.getString("email");
                    pass = b.getString("password");
                    date = b.getString("dob");
                    gender = b.getString("gender");
                    AddChef chef = new AddChef(id, nm , mail , pass , date, gender);
                    reference.child(id).setValue(chef);
                    Toast.makeText(getApplicationContext(), "Registration Successful ", Toast.LENGTH_LONG).show();
                }



    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch(menuItem.getItemId())
        {
            case R.id.navigation_home:
                fragment = new home_fragment();
                break;

            case R.id.navigation_dashboard:
                fragment = new dashboard();
                break;

            case R.id.navigation_addRecipe:
                fragment = new addRecipe();
                break;

            case R.id.profile:
                fragment = new profile();
                break;
        }
        return loadFragment(fragment);
    }
}
