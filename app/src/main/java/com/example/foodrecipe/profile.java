package com.example.foodrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends Fragment implements View.OnClickListener {

    TextView nm,mail,gen,date;
    DatabaseReference reference;
    FirebaseAuth auth;
    Button logout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fr =  inflater.inflate(R.layout.profile, null);

        nm = fr.findViewById(R.id.name);
        mail = fr.findViewById(R.id.mail);
        gen = fr.findViewById(R.id.gender);
        date = fr.findViewById(R.id.dob);
        logout=fr.findViewById(R.id.logout);

        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("chefDetails").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name , dob, gender,  email;
                name = dataSnapshot.child("chefName").getValue().toString();
                dob = dataSnapshot.child("chefDob").getValue().toString();
                gender = dataSnapshot.child("chefGender").getValue().toString();
                email = dataSnapshot.child("chefMail").getValue().toString();

                nm.setText(name);
                mail.setText(email);
                gen.setText(gender);
                date.setText(dob);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        logout.setOnClickListener(this);
        return  fr;

    }

    @Override
    public void onClick(View v) {

        auth.signOut();

    }
}
