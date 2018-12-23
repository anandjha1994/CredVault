package com.app.credentialvault.credvault.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.adapters.ParentAdapter;
import com.app.credentialvault.credvault.model.BasicLoginInfo;
import com.app.credentialvault.credvault.model.Card;
import com.app.credentialvault.credvault.model.Child;
import com.app.credentialvault.credvault.model.CredRepo;
import com.app.credentialvault.credvault.model.CredvaultAuthenticationData;
import com.app.credentialvault.credvault.model.Notes;
import com.app.credentialvault.credvault.model.Parent;
import com.app.credentialvault.credvault.model.WebSiteAuth;
import com.app.credentialvault.credvault.utils.AuthenticationType;
import com.app.credentialvault.credvault.utils.Constants;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton addWebsiteFamButton;
    private FloatingActionButton addBasicAuthFamButton;
    private FloatingActionButton addNewCardFamButton;
    private FloatingActionButton addNewNoteFamButton;

    private DatabaseReference databaseReference;
    private FloatingActionMenu floatingActionMenu;

    private HashMap<String,Card> favoriteCards=new HashMap<>();
    private HashMap<String,BasicLoginInfo> favoriteBasicInfo=new HashMap<>();
    private HashMap<String,Notes> favoriteNotes=new HashMap<>();
    private HashMap<String,WebSiteAuth> favoriteWebCred=new HashMap<>();

    private HashMap<String,Card> allCards=new HashMap<>();
    private HashMap<String,BasicLoginInfo> allBasicInfo=new HashMap<>();
    private HashMap<String,Notes> allNotes=new HashMap<>();
    private HashMap<String,WebSiteAuth> allWebCred=new HashMap<>();

    private RecyclerView recyclerView;
    private ParentAdapter parentAdapter;
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView= findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        addWebsiteFamButton= findViewById(R.id.addWebsiteButton);
        addBasicAuthFamButton=  findViewById(R.id.addBasicAuthButton);
        addNewCardFamButton =  findViewById(R.id.addNewCardButton);
        addNewNoteFamButton =  findViewById(R.id.addNotes);
        floatingActionMenu= findViewById(R.id.fam);
        floatingActionMenu.setClosedOnTouchOutside(true);
        databaseReference= FirebaseDatabase.getInstance().getReference().child(Constants.USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        addNewNoteFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddNoteActivity=new Intent(getApplicationContext(),NotesActivity.class);
                startActivity(goToAddNoteActivity);
            }
        });

        addNewCardFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddCardActivity= new Intent(getApplicationContext(), CardDetailActivity.class);
                startActivity(goToAddCardActivity);
            }
        });
        addWebsiteFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddWebsiteActivity= new Intent(getApplicationContext(), NewWebsiteActivity.class);
                startActivity(goToAddWebsiteActivity);
            }
        });

        addBasicAuthFamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddBasicAuthActivity= new Intent(getApplicationContext(), BasicAuthenticationActivity.class);
                startActivity(goToAddBasicAuthActivity);

            }
        });
        fillUserData(databaseReference);

}

    private void fillUserData(DatabaseReference databaseReference) {
        //Basic auth data
        databaseReference.child(Constants.TYPE_BASIC).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> s= dataSnapshot.getChildren();
                for(DataSnapshot ds: s){
                    BasicLoginInfo basicLoginData=ds.getValue(BasicLoginInfo.class);
                    allBasicInfo.put(basicLoginData.getId(),basicLoginData);
                    if(basicLoginData.isFavourite()){
                        favoriteBasicInfo.put(basicLoginData.getId(),basicLoginData);
                    }
                }
                CredvaultAuthenticationData.setAllBasicAuthentication(allBasicInfo);
                CredvaultAuthenticationData.setAllFavBasicAuthentication(favoriteBasicInfo);
                populateRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Card data
        databaseReference.child(Constants.TYPE_CARD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> s= dataSnapshot.getChildren();
                for(DataSnapshot ds: s){
                    Card card=ds.getValue(Card.class);
                    allCards.put(card.getId(),card);
                    if(card.isFavorite()){
                        favoriteCards.put(card.getId(),card);
                    }
                }
                CredvaultAuthenticationData.setAllCardAuthentication(allCards);
                CredvaultAuthenticationData.setAllFavCardAuthentication(favoriteCards);
                populateRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Website data
        databaseReference.child(Constants.TYPE_WEBSITE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> s= dataSnapshot.getChildren();
                for(DataSnapshot ds: s){
                    WebSiteAuth webSite=ds.getValue(WebSiteAuth.class);
                    allWebCred.put(webSite.getId(),webSite);
                    if(webSite.isFavorite()){
                        favoriteWebCred.put(webSite.getId(),webSite);
                    }
                }
                CredvaultAuthenticationData.setAllWebpageAuthentication(allWebCred);
                CredvaultAuthenticationData.setAllFavWebpageAuthentication(favoriteWebCred);
                populateRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Note data
        databaseReference.child(Constants.TYPE_NOTE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> s= dataSnapshot.getChildren();
                for(DataSnapshot ds: s){
                    Notes notes=ds.getValue(Notes.class);
                    allNotes.put(notes.getId(),notes);
                    if(notes.isFavorite()){
                        favoriteNotes.put(notes.getId(),notes);
                    }
                }
                CredvaultAuthenticationData.setAllNotesAuthentication(allNotes);
                CredvaultAuthenticationData.setAllFavNotesAuthentication(favoriteNotes);
                populateRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void populateRecyclerView() {
        parentAdapter= new ParentAdapter(makeParentChild());
        recyclerView.setAdapter(parentAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<? extends ExpandableGroup> makeParentChild() {
        List<Parent> parents= new ArrayList<>();
        List<Child> childList=allCards();
        Parent parent= new Parent("Cards",childList ,R.drawable.ic_creditcard,childList.size() );
        parents.add(parent);
        childList=allBasicAuth();
        parent=new Parent("Basic Auth", childList, R.drawable.ic_basic,childList.size());
        parents.add(parent);
        childList=allWebPageAuth();
        parent=new Parent("Web Page Auth", allWebPageAuth(), R.drawable.ic_basic,childList.size());
        parents.add(parent);
        childList=allNotes();
        parent=new Parent("Notes", allNotes(), R.drawable.ic_basic,childList.size());
        parents.add(parent);
        return parents;
    }

    private List<Child> allBasicAuth() {
        List<Child> basicInfo=new ArrayList<>();
        if(null!=CredvaultAuthenticationData.getAllBasicAuthentication()) {
            for (BasicLoginInfo info : CredvaultAuthenticationData.getAllBasicAuthentication().values()) {
                basicInfo.add(new Child(this,info.getName(), R.drawable.ic_basic, info.getId(), AuthenticationType.BASIC));
            }
            return basicInfo;
        }
        return Collections.emptyList();
    }

    private List<Child> allCards() {
        List<Child> cards=new ArrayList<>();
        if(null!=CredvaultAuthenticationData.getAllCardAuthentication()) {
            for (Card info : CredvaultAuthenticationData.getAllCardAuthentication().values()) {
                cards.add(new Child(this,info.getName(), R.drawable.ic_creditcard, info.getId(), AuthenticationType.BASIC));
            }
            return cards;
        }
        return Collections.emptyList();
    }

    private List<Child> allWebPageAuth() {
        List<Child> webPageList=new ArrayList<>();
        if(null!=CredvaultAuthenticationData.getAllBasicAuthentication()) {
            for (WebSiteAuth info : CredvaultAuthenticationData.getAllWebpageAuthentication().values()) {
                webPageList.add(new Child(this,info.getName(), R.drawable.ic_web, info.getId(), AuthenticationType.BASIC));
            }
            return webPageList;
        }
        return Collections.emptyList();
    }

    private List<Child> allNotes() {
        List<Child> notes=new ArrayList<>();
        if(null!=CredvaultAuthenticationData.getAllNotesAuthentication()) {
            for (Notes info : CredvaultAuthenticationData.getAllNotesAuthentication().values()) {
                notes.add(new Child(this,info.getTitle(), R.drawable.ic_note, info.getId(), AuthenticationType.BASIC));
            }
            return notes;
        }
        return Collections.emptyList();
    }
}
