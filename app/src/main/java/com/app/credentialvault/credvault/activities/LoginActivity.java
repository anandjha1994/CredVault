package com.app.credentialvault.credvault.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
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

    private DatabaseReference databaseUserCred;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        userName=(EditText) findViewById(R.id.logInUser);
        passWord=(EditText) findViewById(R.id.logInPassword);
        logInButton=(Button) findViewById(R.id.logInButton);
        redirectRegisterButton=(Button) findViewById(R.id.redirectRegisterButton);

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
                            Intent goTOHome= new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(goTOHome);
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
