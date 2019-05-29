package com.example.foodrecipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    SignInButton GoogleSignInB;
    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    Button chefLogin;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chefLogin = (Button) findViewById(R.id.chefLogin);
        chefLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),chefDetails.class);
                startActivity(i);

            }
        });

        pd = new ProgressDialog(this);
        pd.setMessage("Signing in...");

        GoogleSignInB = (SignInButton) findViewById(R.id.sign_in_button);
        auth = FirebaseAuth.getInstance();
        GoogleSignInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                // Configure sign-in to request the user's ID, email address, and basic
                // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.google_server_client_id))
                        .requestEmail()
                        .build();

                mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        //FirebaseUser account = auth.getCurrentUser();
        //getAuthentication(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 101)
        {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)

    {
        try {

            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            getAuthentication(account);

            //Toast.makeText(this, "SignIn successfully", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "Error in SignIn", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAuthentication(GoogleSignInAccount account) {


        // Sign in success, update UI with the signed-in user's information
        Log.d("HERE","firebaseAuthWithGOOGLE: "+account.getIdToken());

        AuthCredential cred = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        Log.d("HERE","firebaseAuthWithGOOGLE: "+cred.toString());

        auth.signInWithCredential(cred).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    pd.dismiss();
                    FirebaseUser user = auth.getCurrentUser();
                    String name, mail, url;
                    name = user.getDisplayName();
                    mail = user.getEmail();
                    url = user.getPhotoUrl().toString();

                    Log.d("Details",name+" / "+mail);
                    Intent i = new Intent(getApplicationContext(), userPannel.class);
                    i.putExtra("Name", name);
                    i.putExtra("Mail", mail);
                    i.putExtra("Photo", url);
                    startActivity(i);
                    //Toast.makeText(MainActivity.this, "Signed IN", Toast.LENGTH_SHORT).show();
                }
                else
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this, "Sign in Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

