package com.app.credentialvault.credvault.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.BasicLoginInfo;
import com.app.credentialvault.credvault.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BasicAuthenticationActivity extends AppCompatActivity {

    private TextInputLayout textInputName;
    private TextInputLayout textInputUserName;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputAdditional;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputNotes;
    private AppCompatCheckBox checkBoxFavourite;

    private Button saveButton;

    private DatabaseReference databaseUserCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_authentication);

        textInputName=findViewById(R.id.inputTxtName);
        textInputUserName=findViewById(R.id.inputTxtUserName);
        textInputPassword=findViewById(R.id.inputTxtPassword);
        textInputAdditional=findViewById(R.id.inputTxtNote);
        textInputEmail=findViewById(R.id.inputTxtEmail);
        textInputNotes=findViewById(R.id.inputTxtNote);
        checkBoxFavourite=findViewById(R.id.checkbox_favourite);
        saveButton=findViewById(R.id.buttonSave);

        databaseUserCred= FirebaseDatabase.getInstance().getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.TYPE_BASIC);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    private void save(){
        BasicLoginInfo basicLoginInfo=getBasicLogInInfo();
        databaseUserCred.child(basicLoginInfo.getId()).setValue(basicLoginInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(BasicAuthenticationActivity.this, Constants.SUCCESSFUL, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BasicAuthenticationActivity.this, Constants.FAILED, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private BasicLoginInfo getBasicLogInInfo(){
       BasicLoginInfo info=new BasicLoginInfo();
       info.setId(databaseUserCred.push().getKey());
       info.setName(textInputName.getEditText().getText().toString());
       info.setUserName(textInputUserName.getEditText().getText().toString());
       info.setPassword(textInputUserName.getEditText().getText().toString());
       info.setAdditional(textInputUserName.getEditText().getText().toString());
       info.setEmail(textInputEmail.getEditText().getText().toString());
       info.setAdditional(textInputNotes.getEditText().getText().toString());
       info.setIsFavourite(checkBoxFavourite.isChecked()?Constants.YES:Constants.NO);
       return info;
    }
}
