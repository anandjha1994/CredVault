package com.app.credentialvault.credvault.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.Card;
import com.app.credentialvault.credvault.model.CredvaultAuthenticationData;
import com.app.credentialvault.credvault.model.WebSiteAuth;
import com.app.credentialvault.credvault.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NewWebsiteActivity extends AppCompatActivity {

    private TextInputEditText websiteNameEditText;
    private TextInputEditText urlEditText;
    private TextInputEditText userNameEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText noteEditText;
    private AppCompatCheckBox checkBoxFavourite;

    private ImageButton copyWebName;
    private ImageButton copyWebUsername;
    private ImageButton copyWebUrl;
    private ImageButton copyWebPassword;
    private ImageButton copyWebNotes;

    private ImageButton editWebName;
    private ImageButton editWebUsername;
    private ImageButton editWebUrl;
    private ImageButton editWebPassword;
    private ImageButton editWebNote;

    private ClipboardManager clipboardManager;
    private ClipData clipData;

    private WebSiteAuth webSiteAuthModel;

    private Button saveButton;

    private Toolbar toolbar;

    private DatabaseReference databaseUserCred;
    private boolean isInEditMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_website);
        bindAllFormElements();
        setSupportActionBar(toolbar);
        //Implementation for toolbar is remaining.. TODO

        Bundle extra=getIntent().getExtras();
        if(extra!=null) {
            String objId = extra.getString("refObjId");
            loadWebSiteAuthAndSetProperties(objId);
            setVisibilityOfEditAndCopyComponents();
            bindCopyAndEditComponents();
            Toast.makeText(NewWebsiteActivity.this, objId, Toast.LENGTH_SHORT).show();
        }

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        databaseUserCred= FirebaseDatabase.getInstance().getReference().child(Constants.USER).child(user.getUid()).child(Constants.TYPE_WEBSITE);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check and fill User object with validation and then save

                if(!isInEditMode) {
                    webSiteAuthModel = new WebSiteAuth();
                }
                fillModelFromForm(webSiteAuthModel);
                save();
            }
        });
    }

    private void bindCopyAndEditComponents() {
        copyWebName=findViewById(R.id.buttonCopyWebsiteName);
        copyWebPassword=findViewById(R.id.buttonCopyWebsitePassword);
        copyWebNotes=findViewById(R.id.buttonCopyWebsiteNote);
        copyWebUrl=findViewById(R.id.buttonCopyWebsiteUrl);
        copyWebUsername=findViewById(R.id.buttonCopyWebsiteUsername);

        editWebName=findViewById(R.id.buttonEditWebsiteName);
        editWebUsername=findViewById(R.id.buttonEditWebsiteUsername);
        editWebUrl=findViewById(R.id.buttonEditWebsiteUrl);
        editWebPassword=findViewById(R.id.buttonEditWebsitePassword);
        editWebNote=findViewById(R.id.buttonEditWebsiteNote);

        assignOnClickListenerForCopyComponents(copyWebName,"name");
        assignOnClickListenerForCopyComponents(copyWebUrl,"url");
        assignOnClickListenerForCopyComponents(copyWebUsername,"userName");
        assignOnClickListenerForCopyComponents(copyWebPassword,"password");
        assignOnClickListenerForCopyComponents(copyWebNotes,"note");

        assignOnClickListenerForEditComponents(editWebName,"name");
        assignOnClickListenerForEditComponents(editWebUsername,"userName");
        assignOnClickListenerForEditComponents(editWebPassword,"password");
        assignOnClickListenerForEditComponents(editWebUrl,"url");
        assignOnClickListenerForEditComponents(editWebNote,"note");

    }

    private void assignOnClickListenerForEditComponents(ImageButton button, final String forField) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (forField){
                    case "name":
                        enableFormEditComponents(websiteNameEditText);
                        break;
                    case "url":
                        enableFormEditComponents(urlEditText);
                        break;
                    case "userName":
                        enableFormEditComponents(userNameEditText);
                        break;
                    case "password":
                        enableFormEditComponents(passwordEditText);
                        break;
                    case "note":
                        enableFormEditComponents(noteEditText);
                        break;
                }

            }
        });
    }

    private void assignOnClickListenerForCopyComponents(ImageButton button, final String forField) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                switch (forField){
                    case "name":
                        clipData= ClipData.newPlainText("",websiteNameEditText.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(NewWebsiteActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "userName":
                        clipData=ClipData.newPlainText("",userNameEditText.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(NewWebsiteActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "url":
                        clipData=ClipData.newPlainText("",urlEditText.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(NewWebsiteActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "password":
                        clipData=ClipData.newPlainText("",passwordEditText.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(NewWebsiteActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "note":
                        clipData=ClipData.newPlainText("",noteEditText.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(NewWebsiteActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setVisibilityOfEditAndCopyComponents() {
        findViewById(R.id.relativeLayoutWebsiteCopySectionNote).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutWebsiteCopySectionPassword).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutWebsiteCopySectionUrl).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutWebsiteCopySectionUsername).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutWebsiteCopySectionName).setVisibility(View.VISIBLE);

        findViewById(R.id.relativeLayoutWebsiteEditSectionName).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutWebsiteEditSectionNote).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutWebsiteEditSectionUrl).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutWebsiteEditSectionUsername).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutWebsiteEditSectionPassword).setVisibility(View.VISIBLE);
    }

    private void loadWebSiteAuthAndSetProperties(String objId) {
            HashMap<String, WebSiteAuth> allWebAuth= CredvaultAuthenticationData.getAllWebpageAuthentication();
            webSiteAuthModel= allWebAuth.get(objId);
            websiteNameEditText.setText(webSiteAuthModel.getName());
            urlEditText.setText(webSiteAuthModel.getUrl());
            userNameEditText.setText(webSiteAuthModel.getUserName());
            passwordEditText.setText(webSiteAuthModel.getPassword());
            noteEditText.setText(webSiteAuthModel.getAdditional());
            checkBoxFavourite.setChecked(webSiteAuthModel.isFavorite());

            disableFormEditComponents(websiteNameEditText);
            disableFormEditComponents(urlEditText);
            disableFormEditComponents(userNameEditText);
            disableFormEditComponents(passwordEditText);
            disableFormEditComponents(noteEditText);

            isInEditMode=true;
            // saveButton.setEnabled(false);
    }

    private void enableFormEditComponents(TextInputEditText inputField) {
        inputField.setEnabled(true);
        inputField.requestFocus();
        inputField.setSelection(inputField.getText().length());
    }
    private void disableFormEditComponents(TextInputEditText inputField) {
        inputField.setEnabled(false);
    }

    private void bindAllFormElements() {
        saveButton= findViewById(R.id.credSaveNewWeb);
        websiteNameEditText=findViewById(R.id.editTextWebsiteName);
        urlEditText=findViewById(R.id.editTextWebsiteUrl);
        userNameEditText=findViewById(R.id.editTextWebsiteUserName);
        passwordEditText=findViewById(R.id.editTextWebsitePassword);
        noteEditText=findViewById(R.id.editTextWebsiteNote);
        checkBoxFavourite=findViewById(R.id.credCheckBoxFavNewWeb);

        toolbar=findViewById(R.id.toolbar);
    }

    private void fillModelFromForm(WebSiteAuth webSiteAuth){
        //converting into object
      if(webSiteAuth.getId()==null) {
          webSiteAuth.setId(databaseUserCred.push().getKey());
      }
        webSiteAuth.setUserName(userNameEditText.getText().toString());
        webSiteAuth.setPassword(passwordEditText.getText().toString());
        webSiteAuth.setUrl(urlEditText.getText().toString());
        webSiteAuth.setAdditional(noteEditText.getText().toString());
        webSiteAuth.setFavorite(checkBoxFavourite.isChecked());
    }

    private void save(){
        databaseUserCred.child(webSiteAuthModel.getId()).setValue(webSiteAuthModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(NewWebsiteActivity.this, "Successful ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(NewWebsiteActivity.this, "Failed ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
