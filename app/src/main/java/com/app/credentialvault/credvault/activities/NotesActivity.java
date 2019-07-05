package com.app.credentialvault.credvault.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.Card;
import com.app.credentialvault.credvault.model.CredvaultAuthenticationData;
import com.app.credentialvault.credvault.model.Notes;
import com.app.credentialvault.credvault.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NotesActivity extends AppCompatActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText noteEditText;
    private AppCompatCheckBox favorite;
    private Button saveButton;

    private ImageButton copyNoteTitle;
    private ImageButton copyNoteText;
    private ImageButton editNoteTitle;
    private ImageButton editNoteText;

    private DatabaseReference databaseReference;

    private ClipboardManager clipboardManager;
    private ClipData clipData;

    private Notes notes;
    private boolean isInEditMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        bindAllFormElements();

        Bundle extra=getIntent().getExtras();
        if(extra!=null) {
            String objId = extra.getString("refObjId");
            loadNoteDetailsAndSetProperties(objId);
            setVisibilityOfEditAndCopyComponents();
            bindCopyAndEditComponents();
            Toast.makeText(NotesActivity.this, objId, Toast.LENGTH_SHORT).show();
        }

        databaseReference= FirebaseDatabase.getInstance().getReference().child(Constants.USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.TYPE_NOTE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isInEditMode) {
                    notes=new Notes();
                }
                fillNoteFromForm(notes);
                save();
            }

            private void save() {
                databaseReference.child(notes.getId()).setValue(notes).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(NotesActivity.this, Constants.SUCCESSFUL, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(NotesActivity.this, Constants.FAILED, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void bindCopyAndEditComponents() {
        copyNoteTitle=findViewById(R.id.buttonCopyNoteTitle);
        copyNoteText=findViewById(R.id.buttonCopyNoteNote);
        editNoteText=findViewById(R.id.buttonEditNoteNote);
        editNoteTitle=findViewById(R.id.buttonEditNoteTitle);

        assignOnClickListenerForCopyComponents(copyNoteTitle,"title");
        assignOnClickListenerForCopyComponents(copyNoteText,"note");

        assignOnClickListenerForEditComponents(editNoteText,"note");
        assignOnClickListenerForEditComponents(editNoteTitle,"title");
    }

    private void assignOnClickListenerForEditComponents(ImageButton button, final String forField) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (forField){
                    case "title":
                        enableFormEditComponents(titleEditText);
                        break;
                    case "note":
                        enableFormEditComponents(noteEditText);
                        break;
                }

            }
        });
    }

    private void enableFormEditComponents(TextInputEditText inputField) {
        inputField.setEnabled(true);
        inputField.requestFocus();
        inputField.setSelection(inputField.getText().length());
    }

    private void disableFormEditComponents(TextInputEditText inputField) {
        inputField.setEnabled(false);
    }

    private void assignOnClickListenerForCopyComponents(ImageButton button, final String forField) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                switch (forField){
                    case "title":
                        clipData= ClipData.newPlainText("",titleEditText.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(NotesActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "note":
                        clipData=ClipData.newPlainText("",noteEditText.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(NotesActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setVisibilityOfEditAndCopyComponents() {
        findViewById(R.id.relativeLayoutNoteCopySectionTitle).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutNoteCopyNote).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutNoteEditNote).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutNoteEditSectionTitle).setVisibility(View.VISIBLE);
    }

    private void loadNoteDetailsAndSetProperties(String objId) {
        HashMap<String, Notes> allCards= CredvaultAuthenticationData.getAllNotesAuthentication();
        notes= allCards.get(objId);
        titleEditText.setText(notes.getTitle());
        noteEditText.setText(notes.getNote());

        disableFormEditComponents(titleEditText);
        disableFormEditComponents(noteEditText);
        isInEditMode=true;
    }

    private void bindAllFormElements() {
        titleEditText=findViewById(R.id.editTextNoteTitle);
        noteEditText=findViewById(R.id.editTextNote);
        favorite=findViewById(R.id.credCheckBoxFavNewNote);
        saveButton=findViewById(R.id.credSaveNewNote);


    }

    private void fillNoteFromForm(Notes notes) {
        if(notes.getId()==null) {
            notes.setId(databaseReference.push().getKey());
        }
        notes.setFavorite(favorite.isChecked());
        notes.setTitle(titleEditText.getText().toString());
        notes.setNote(noteEditText.getText().toString());
    }
}
