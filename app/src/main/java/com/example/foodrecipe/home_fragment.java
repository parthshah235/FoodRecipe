package com.example.foodrecipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home_fragment extends Fragment {

    FirebaseDatabase dr;
    DatabaseReference r;
    ListView listView;
    ArrayList<String> list,imageList,descList,cuisineList;
    ArrayAdapter<String> adapter;
    retriveRecipe rr;
    String imgpath;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View f = inflater.inflate(R.layout.home, null);

        rr = new retriveRecipe();
        listView = f.findViewById(R.id.listView1);

        final customAdapter c = new customAdapter();

        list= new ArrayList<>();
        imageList=new ArrayList<>();
        descList=new ArrayList<>();

        dr = FirebaseDatabase.getInstance();
        r = dr.getReference("Recipe");
        /*Query query =  FirebaseDatabase.getInstance().getReference().child("Recipe").orderByChild("cuisine").equalTo("Gujarati");
        //reference = FirebaseDatabase.getInstance().getReference().child("Recipe").orderByChild("cuisine").equalTo("Gujarati");
        Log.i("R", r.toString());
        Log.i("Query Result",query.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    rr = ds.getValue(retriveRecipe.class);
                    list.add(rr.getRecipeName().toString());
                    imageList.add(rr.getImage().toString());
                    descList.add(rr.getRecipeDescription().toString());
                    //cuisineList.add(rr.getCuisine().toString());

                }

                listView.setAdapter(c);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    for(DataSnapshot d : data.getChildren())
                    {
                        //if(d.getValue(retriveRecipe.class).getCuisine().equals("Gujarati"))
                        //{
                            rr = d.getValue(retriveRecipe.class);
                            list.add(rr.getRecipeName().toString());
                            imageList.add(rr.getImagePath().toString());
                            //imgpath = dataSnapshot.child("imagePath").getValue().toString();
                            //Log.d("IMAGEPATH",imgpath);
                            descList.add(rr.getRecipeDescription().toString());

                            Log.i("RECIPE",rr.getRecipeName()+rr.getCuisine());
                            //listview
                            listView.setAdapter(c);
                        //}
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return  f;
    }

    class customAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Log.d("abcd","aaayu");
            view = getLayoutInflater().inflate(R.layout.recipes_retrive,null);
            final ImageView imageView = view.findViewById(R.id.recipe_image);
            TextView n = view.findViewById(R.id.recipe_name);
            TextView d = view.findViewById(R.id.recipe_Desc);
            //TextView c = view.findViewById(R.id.recipe_Cuisine);
            n.setText(list.get(position));
            d.setText(descList.get(position));
            Glide.with(getContext()).load(imageList.get(position)).into(imageView);

            return view;
        }
    }
}
