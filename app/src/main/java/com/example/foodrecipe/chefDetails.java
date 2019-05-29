package com.example.foodrecipe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chefDetails extends AppCompatActivity {

    EditText name,password,email,dob;
    Button register,login;
    RadioGroup genderRadioGroup;
    //FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference reference;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_details);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.signup_input_name);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.btn_signup);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        dob = (EditText) findViewById(R.id.dob);
        login = (Button) findViewById(R.id.btn_login);

        //database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("chefDetails");
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addChef();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), chefLogin.class);
                startActivity(i);
            }
        });

        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        //awesomeValidation.addValidation(this, R.id.editTextMobile, "^[2-9]{2}[0-9]{8}$", R.string.nameerror);
        //awesomeValidation.addValidation(this, R.id.dob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);
        //awesomeValidation.addValidation(this, R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);


    }

    void addChef()
    {

        if (awesomeValidation.validate()) {
            //Toast.makeText(getApplicationContext(), "Validation Successfull", Toast.LENGTH_LONG).show();

            //process the data further


            Log.e("Here", " In Function");
            String nm, mail, pass, gender;
            String date;
            //String id;
            nm = name.getText().toString().trim();
            mail = email.getText().toString().trim();
            pass = password.getText().toString().trim();
            date = dob.getText().toString().trim();
            gender = name.getText().toString().trim();

            int selectedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
            gender = selectedRadioButton.getText().toString();

            if (!TextUtils.isEmpty(nm)) {
                final Intent i = new Intent(getApplicationContext(), chefPannel.class);
                i.putExtra("name", nm);
                i.putExtra("email", mail);
                i.putExtra("password", pass);
                i.putExtra("dob", date);
                i.putExtra("gender", gender);
                Log.e("Here", mail + pass);
                auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("Here", "Registered");
                            startActivity(i);
                        }
                    }
                });
                //auth.getCurrentUser().getUid();
                //String id = auth.getCurrentUser().getUid();
                // String id = reference.push().getKey();
                /*String id = auth.getUid();
                AddChef chef = new AddChef(id, nm , mail , pass , date, gender);
                reference.child(id).setValue(chef);
                Toast.makeText(this, "Registration Successful ", Toast.LENGTH_LONG).show();*/


                //Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Check all the fields", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,"Enter name",Toast.LENGTH_LONG).show();
        }
    }
}
