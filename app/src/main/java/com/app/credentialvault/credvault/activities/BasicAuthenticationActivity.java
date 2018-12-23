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

    BasicLoginInfo basicLoginInfoModel;

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

        databaseUserCred= FirebaseDatabase.getInstance().getReference().child(Constants.USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.TYPE_BASIC);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basicLoginInfoModel=new BasicLoginInfo();
                fillBasicLogInInfo(basicLoginInfoModel);
                save();
            }
        });
    }

    private void fillBasicLogInInfo(BasicLoginInfo basicLoginInfoModel) {
        basicLoginInfoModel.setId(databaseUserCred.push().getKey());
        basicLoginInfoModel.setName(textInputName.getEditText().getText().toString());
        basicLoginInfoModel.setUserName(textInputUserName.getEditText().getText().toString());
        basicLoginInfoModel.setPassword(textInputPassword.getEditText().getText().toString());
        basicLoginInfoModel.setEmail(textInputEmail.getEditText().getText().toString());
        basicLoginInfoModel.setAdditional(textInputNotes.getEditText().getText().toString());
        basicLoginInfoModel.setFavourite(checkBoxFavourite.isChecked());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    private void save(){
        databaseUserCred.child(basicLoginInfoModel.getId()).setValue(basicLoginInfoModel).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}
