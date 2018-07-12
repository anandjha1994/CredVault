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
import com.google.firebase.auth.FirebaseUser;

public class RegisterNewUserActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private static EditText userName;
    private static EditText passWord;
    private static Button registerButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        mAuth=FirebaseAuth.getInstance();
        userName=(EditText) findViewById(R.id.userName);
        passWord=(EditText) findViewById(R.id.masterPassword);
        registerButton=(Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call registration process
                registerMethod();
            }
        });
    }

    private void registerMethod(){
        mAuth.createUserWithEmailAndPassword(userName.getText().toString(), passWord.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterNewUserActivity.this, "Successful Registered : "+user.getEmail(), Toast.LENGTH_SHORT).show();
                            Intent goToLoginActivity=new Intent(getApplicationContext(), LoginActivity.class);
                            goToLoginActivity.putExtra("email", userName.getText().toString());
                            startActivity(goToLoginActivity);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterNewUserActivity.this, "Failed Registration:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        FirebaseUser user = mAuth.getCurrentUser();
        if(null!=user){
            mAuth.signOut();
        }
        registerButton.setVisibility(View.INVISIBLE);
        userName.setText("");
        passWord.setText("");
    }
}
