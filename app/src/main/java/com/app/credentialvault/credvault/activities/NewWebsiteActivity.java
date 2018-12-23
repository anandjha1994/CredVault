package com.app.credentialvault.credvault.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.User;
import com.app.credentialvault.credvault.model.UserInformations;
import com.app.credentialvault.credvault.model.WebSiteAuth;
import com.app.credentialvault.credvault.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewWebsiteActivity extends AppCompatActivity {

    private TextInputLayout websiteNameEditText;
    private TextInputLayout urlEditText;
    private TextInputLayout userNameEditText;
    private TextInputLayout passwordEditText;
    private TextInputLayout noteEditText;
    private AppCompatCheckBox checkBoxFavourite;

    private Button saveButton;

    private DatabaseReference databaseUserCred;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_website);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        databaseUserCred= FirebaseDatabase.getInstance().getReference().child(Constants.USER).child(user.getUid()).child(Constants.TYPE_WEBSITE);
        saveButton= (Button) findViewById(R.id.credSaveNewWeb);
        websiteNameEditText= (TextInputLayout) findViewById(R.id.credInputNewWebName);
        urlEditText= (TextInputLayout) findViewById(R.id.credInputNewWebURL);
        userNameEditText= (TextInputLayout) findViewById(R.id.credInputNewWebUserName);
        passwordEditText= (TextInputLayout) findViewById(R.id.credInputNewWebPassword);
        noteEditText=(TextInputLayout) findViewById(R.id.credInputNewWebNotes);

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
      webSiteAuth.setUserName(userNameEditText.getEditText().getText().toString());
      webSiteAuth.setPassword(passwordEditText.getEditText().getText().toString());
      webSiteAuth.setUrl(urlEditText.getEditText().getText().toString());
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
