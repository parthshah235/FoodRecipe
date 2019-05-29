package com.example.foodrecipe;

import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class dashboard extends Fragment {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> list,imageList,descList,cuisineList,ingredientsList;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    retriveRecipe rr;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fr =  inflater.inflate(R.layout.dashboard, null);

        rr = new retriveRecipe();
        listView = fr.findViewById(R.id.listView1);
        firebaseAuth = FirebaseAuth.getInstance();
        String id1 = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe").child(id1);

       // FirebaseStorage storage = FirebaseStorage.getInstance();
       // StorageReference storageRef = storage.getReferenceFromUrl("gs://foodrecipe-d21ab.appspot.com");    //change the url according to your firebase app
        //StorageReference storageRefTemp = storage.getReference();
//        final StorageReference childRef = storageRef.child("images/"+id1);

        final  customAdapter ca = new customAdapter();




        list= new ArrayList<>();
        imageList=new ArrayList<>();
        descList=new ArrayList<>();
        cuisineList=new ArrayList<>();
        ingredientsList=new ArrayList<>();

        //adapter = new ArrayAdapter<String>(getContext(),R.layout.recipes_retrive,R.id.recipe_name,list);
        //adapter1 = new ArrayAdapter<String>(getContext(),R.layout.recipes_retrive,R.id.image_recipe);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    rr = ds.getValue(retriveRecipe.class);
                    list.add(rr.getRecipeName().toString());
                    imageList.add(rr.getImagePath().toString());
                    descList.add(rr.getRecipeDescription().toString());
                    cuisineList.add(rr.getCuisine().toString());
                    ingredientsList.add(rr.getrecipeIngredients().toString());

                }

                listView.setAdapter(ca);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return fr;


    }

    class customAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return imageList.size();
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
            TextView c = view.findViewById(R.id.recipe_Cuisine);
            TextView i = view.findViewById(R.id.recipe_Ing);
            n.setText(list.get(position));
            d.setText(descList.get(position));
            c.setText(cuisineList.get(position));
            i.setText(ingredientsList.get(position));

            //String demoLoad = "https://firebasestorage.googleapis.com/v0/b/foodrecipe-d21ab.appspot.com/o/images%2FiJxl598bcqW5oGvzANaYr5HiiG52%2FManchurian%20Dry.jpeg?alt=media&token=f79b3827-a858-4e15-b6cc-211b46266f8b";
            Log.d("image",imageList.get(position));
            Glide.with(getContext()).load(imageList.get(position)).into(imageView);

            /*FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://foodrecipe-d21ab.appspot.com");
            //Picasso.get().load(imageList.get(position)).into(imageView);
            StorageReference childRef = storageRef.child("images/").child(firebaseAuth.getCurrentUser().getUid()).child(list.get(position)+".jpeg");
            Log.i("StoragePath", childRef.getPath());
            //StorageReference childReff = childRef.child("/ggg.jpeg");
            childRef.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("Failure", e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.i("Tuts+", "uri: " + uri.toString());
                    Glide.with(getContext()).load(uri).into(imageView);
                    //Handle whatever you're going to do with the URL here
                }

            });*/

            return view;
        }
    }

}

