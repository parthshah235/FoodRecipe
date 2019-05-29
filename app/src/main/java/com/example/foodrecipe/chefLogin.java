package com.example.foodrecipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class chefLogin extends AppCompatActivity {

    Button login;
    EditText mail, pass;
    FirebaseAuth mAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login);

        login = findViewById(R.id.bt_login);
        mail = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);

        pd = new ProgressDialog(this);
        pd.setMessage("Signing in...");

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                Log.e("Onclick","Here");
                login(mail.getText().toString(), pass.getText().toString());
            }
        });

    }

    private void login(String email, String password) {
        Log.e("Login:",email+" "+password);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            pd.dismiss();
                            Log.e("SUCCESS","Login");
                            Intent intnt = new Intent(chefLogin.this, chefPannel.class);
                            startActivity(intnt);
                            //done
                        }
                    }
                });
    }
}
