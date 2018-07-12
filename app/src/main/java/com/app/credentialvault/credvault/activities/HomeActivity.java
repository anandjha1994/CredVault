package com.app.credentialvault.credvault.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.adapters.ParentAdapter;
import com.app.credentialvault.credvault.model.BasicLoginInfo;
import com.app.credentialvault.credvault.model.Child;
import com.app.credentialvault.credvault.model.Parent;
import com.app.credentialvault.credvault.utils.Constants;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton addWebsite;
    private FloatingActionButton addBasicAuthButton;
    private FloatingActionButton addNewCardButton;
    private DatabaseReference databaseUserCred;
    private FloatingActionMenu floatingActionMenu;
    private List<BasicLoginInfo> basicInfos= new ArrayList<BasicLoginInfo>();

    private RecyclerView recyclerView;
    private ParentAdapter parentAdapter;
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        addWebsite= (FloatingActionButton) findViewById(R.id.addWebsiteButton);
        addBasicAuthButton= (FloatingActionButton) findViewById(R.id.addBasicAuthButton);
        addNewCardButton = (FloatingActionButton) findViewById(R.id.addNewCardButton);
        floatingActionMenu=(FloatingActionMenu) findViewById(R.id.fam);
        floatingActionMenu.setClosedOnTouchOutside(true);
        databaseUserCred= FirebaseDatabase.getInstance().getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.TYPE_BASIC);
        databaseUserCred.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Iterable<DataSnapshot> s= dataSnapshot.getChildren();
               for(DataSnapshot ds: s){
                   BasicLoginInfo info=ds.getValue(BasicLoginInfo.class);
                   basicInfos.add(info);
               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        addWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddWebsiteActivity= new Intent(getApplicationContext(), NewWebsiteActivity.class);
                startActivity(goToAddWebsiteActivity);
            }
        });

        addBasicAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddBasicAuthActivity= new Intent(getApplicationContext(), BasicAuthenticationActivity.class);
                startActivity(goToAddBasicAuthActivity);

            }
        });
        
        populateRecylerView();
/*
        databaseUserCred= FirebaseDatabase.getInstance().getReference();
        saveButton= (Button) findViewById(R.id.saveButton);
        emailIdEditText= (EditText) findViewById(R.id.emailEditText);
        passwordEditText= (EditText) findViewById(R.id.passwordEditText);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validate input and translate
                if(validateAndTranslate()){
                    String id= databaseUserCred.push().getKey();
                   // userCredRepo.setId(id);
                    databaseUserCred.setValue(userCredRepo);
                    Toast.makeText(HomeActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                }
            }
        });*/


/*    private boolean validateAndTranslate(){
        if(emailIdEditText.getText()!=null && passwordEditText.getText()!=null){
            userCredRepo=new CredRepo();
           // userCredRepo.setEmailId(emailIdEditText.getText().toString());
           // userCredRepo.setPassword(passwordEditText.getText().toString());
            return true;
        }
        return false;
    }*/
}

    private void populateRecylerView() {
        parentAdapter= new ParentAdapter(makeParentChild());
        recyclerView.setAdapter(parentAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<? extends ExpandableGroup> makeParentChild() {
        List<Parent> parents= new ArrayList<Parent>();
        Parent parent= new Parent("Cards", getCards(),R.drawable.ic_creditcard);
        parents.add(parent);
        parent=new Parent("Basic Auth", getBasicAuth(), R.drawable.ic_basic);
        parents.add(parent);
        return parents;
    }

    private List<Child> getBasicAuth() {
        Child c1 = new Child("One", R.drawable.ic_basic);
        Child c2 = new Child("Two", R.drawable.ic_basic);
        Child c3 = new Child("Three", R.drawable.ic_basic);
        return Arrays.asList(c1, c2, c3);
    }

    private List<Child> getCards() {
        Child c1 = new Child("SBI", R.drawable.ic_creditcard);
        Child c2 = new Child("Kotak", R.drawable.ic_creditcard);
        Child c3 = new Child("Baroda", R.drawable.ic_creditcard);
        return Arrays.asList(c1, c2, c3);
    }
}
