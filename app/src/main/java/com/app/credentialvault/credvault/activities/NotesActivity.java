package com.app.credentialvault.credvault.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.Card;
import com.app.credentialvault.credvault.model.Notes;
import com.app.credentialvault.credvault.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotesActivity extends AppCompatActivity {

    private TextInputLayout titleEditText;
    private TextInputLayout noteEditText;
    private AppCompatCheckBox favorite;
    private Button saveButton;

    private DatabaseReference databaseReference;

    private Notes notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        titleEditText=findViewById(R.id.credInputNoteTitle);
        noteEditText=findViewById(R.id.credInputNote);
        favorite=findViewById(R.id.credCheckBoxFavNewNote);
        saveButton=findViewById(R.id.credSaveNewNote);

        databaseReference= FirebaseDatabase.getInstance().getReference().child(Constants.USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.TYPE_NOTE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes=new Notes();
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

    private void fillNoteFromForm(Notes notes) {
        notes.setId(databaseReference.push().getKey());
        notes.setFavorite(favorite.isChecked());
        notes.setTitle(titleEditText.getEditText().getText().toString());
        notes.setNote(noteEditText.getEditText().getText().toString());
    }
}
