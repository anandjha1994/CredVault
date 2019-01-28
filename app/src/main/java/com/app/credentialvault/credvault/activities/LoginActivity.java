package com.app.credentialvault.credvault.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.adapters.UserDataAdapter;
import com.app.credentialvault.credvault.model.CredvaultAuthenticationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private static EditText userName;
    private static EditText passWord;
    private static Button logInButton;

    private static Button redirectRegisterButton;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnectivityAndSpeed();
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        userName=findViewById(R.id.logInUser);
        passWord=findViewById(R.id.logInPassword);
        logInButton=findViewById(R.id.logInButton);
        redirectRegisterButton=findViewById(R.id.redirectRegisterButton);

        //Add on Click listener
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call login method
                loginMethod();
            }
        });

        redirectRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect to registration activity
                Intent goToRegisterActivity = new Intent(getApplicationContext(), RegisterNewUserActivity.class);
                startActivity(goToRegisterActivity);
            }
        });


    }

    private int checkConnectivityAndSpeed() {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(null!=networkInfo && networkInfo.isConnectedOrConnecting()){
            return 1;
        }else{
            return 0;
        }
    }

    private void loginMethod(){
        if(null!=mAuth.getCurrentUser()){
            Toast.makeText(LoginActivity.this, "Already logged in session. Logging out...", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }

        //validate input field

        mAuth.signInWithEmailAndPassword(userName.getText().toString(), passWord.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Successful ", Toast.LENGTH_SHORT).show();
                            //redirect to home
                            UserDataAdapter userDataAdapter=new UserDataAdapter();
                            /*try {
                                userDataAdapter.run();
                                userDataAdapter.run();
                                Log.println(Log.INFO,"", String.valueOf(CredvaultAuthenticationData.getAllCardAuthentication().size()));
                            } catch (Exception exception){
                                exception.printStackTrace();
                            }*/
                            Intent goTOHome= new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(goTOHome);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Login:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
