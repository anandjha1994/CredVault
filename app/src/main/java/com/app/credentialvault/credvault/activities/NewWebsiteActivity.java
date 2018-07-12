package com.app.credentialvault.credvault.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.User;
import com.app.credentialvault.credvault.model.UserInformations;
import com.app.credentialvault.credvault.model.WebSiteAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewWebsiteActivity extends AppCompatActivity {

    private EditText websiteNameEditText;
    private EditText urlEditText;
    private EditText userNameEditText;
    private EditText passeordEditText;
    private Button saveButton;

    private DatabaseReference databaseUserCred;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_website);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        databaseUserCred= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("website");
        saveButton= (Button) findViewById(R.id.saveButton);
        websiteNameEditText= (EditText) findViewById(R.id.nameEditText);
        urlEditText= (EditText) findViewById(R.id.urlEditText);
        userNameEditText= (EditText) findViewById(R.id.usernameEditText);
        passeordEditText= (EditText) findViewById(R.id.passwordEditText);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check and fill User object with validation and then save
                WebSiteAuth webSiteAuth=translateUserInformation();
              saveData(webSiteAuth);
            }
        });
    }

    private WebSiteAuth translateUserInformation(){
        //converting into object
      User user= new User();
      UserInformations userInformations=new UserInformations();
      WebSiteAuth webSiteAuth=new WebSiteAuth();
      webSiteAuth.setUserName(userNameEditText.getText().toString());
      webSiteAuth.setPassword(passeordEditText.getText().toString());
      webSiteAuth.setUrl(urlEditText.getText().toString());
      webSiteAuth.setId(databaseUserCred.push().getKey());
      List<WebSiteAuth> webSiteAuths=new ArrayList<WebSiteAuth>();
      userInformations.setWebSiteAuth(webSiteAuths);
      user.setUserInformations(userInformations);
      return webSiteAuth;
    }

    private void saveData(WebSiteAuth webSiteAuth){
        databaseUserCred.child(webSiteAuth.getId()).setValue(webSiteAuth).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(NewWebsiteActivity.this, "Successful ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(NewWebsiteActivity.this, "Failed ", Toast.LENGTH_SHORT).show();
                }
            }
        });
      //  Toast.makeText(NewWebsiteActivity.this, "saved successfully", Toast.LENGTH_SHORT).show();
    }
}
