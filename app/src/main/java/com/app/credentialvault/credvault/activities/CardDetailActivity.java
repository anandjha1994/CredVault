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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.Card;
import com.app.credentialvault.credvault.model.CredvaultAuthenticationData;
import com.app.credentialvault.credvault.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CardDetailActivity extends AppCompatActivity {

    private TextInputEditText cardName;
    private TextInputEditText cardNumber;
    private TextInputEditText cardExpiryDate;
    private TextInputEditText cardCVC;
    private TextInputEditText cardPin;
    private TextInputEditText cardNotes;

    private ImageButton copyCardName;
    private ImageButton copyCardNumber;
    private ImageButton copyCardDate;
    private ImageButton copyCardCvc;
    private ImageButton copyCardPin;
    private ImageButton copyCardNote;
    private ImageButton editCardName;
    private ImageButton editCardNumber;
    private ImageButton editCardDate;
    private ImageButton editCardCvc;
    private ImageButton editCardPin;
    private ImageButton editCardNote;

    private AppCompatCheckBox checkBoxFavourite;

    private Button saveButton;

    private DatabaseReference databaseReference;
    private Card cardModel;

    private ClipboardManager clipboardManager;
    private ClipData clipData;

    private int previousDateLength;
    private boolean isInEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        bindAllFormElements();

        Bundle extra=getIntent().getExtras();
        if(extra!=null) {
            String objId = extra.getString("refObjId");
            loadCardDetailsAndSetProperties(objId);
            setVisibilityOfEditAndCopyComponents();
            bindCopyAndEditComponents();
            Toast.makeText(CardDetailActivity.this, objId, Toast.LENGTH_SHORT).show();
        }
        databaseReference= FirebaseDatabase.getInstance().getReference().child(Constants.USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.TYPE_CARD);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isInEditMode) {
                    cardModel = new Card();
                }
                fillCardFromForm(cardModel);
                save();
            }

            private void save() {
                databaseReference.child(cardModel.getId()).setValue(cardModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CardDetailActivity.this, Constants.SUCCESSFUL, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CardDetailActivity.this, Constants.FAILED, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        cardExpiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousDateLength=charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (previousDateLength<editable.length() && editable.length()==2){
                    editable.insert(2,"/");
                }
                if(editable.length()>=3){
                     char backSlash= editable.charAt(2);
                     if(backSlash!='/'){
                         editable.insert(2,"/");
                     }
                }
            }
        });
    }

    private void bindCopyAndEditComponents() {
        copyCardName=findViewById(R.id.buttonCopyCardName);
        copyCardNumber=findViewById(R.id.buttonCopyCardNumber);
        copyCardDate=findViewById(R.id.buttonCopyCardDate);
        copyCardCvc=findViewById(R.id.buttonCopyCardCvc);
        copyCardPin=findViewById(R.id.buttonCopyCardPin);
        copyCardNote=findViewById(R.id.buttonCopyCardNote);

        editCardName=findViewById(R.id.buttonEditCardName);
        editCardNumber=findViewById(R.id.buttonEditCardNumber);
        editCardDate=findViewById(R.id.buttonEditCardDate);
        editCardCvc=findViewById(R.id.buttonEditCardCvc);
        editCardPin=findViewById(R.id.buttonEditCardPin);
        editCardNote=findViewById(R.id.buttonEditCardNote);

        assignOnClickListenerForCopyComponents(copyCardName,"name");
        assignOnClickListenerForCopyComponents(copyCardNumber,"number");
        assignOnClickListenerForCopyComponents(copyCardDate,"date");
        assignOnClickListenerForCopyComponents(copyCardCvc,"cvc");
        assignOnClickListenerForCopyComponents(copyCardPin,"pin");
        assignOnClickListenerForCopyComponents(copyCardNote,"note");

        assignOnClickListenerForEditComponents(editCardName,"name");
        assignOnClickListenerForEditComponents(editCardNumber,"number");
        assignOnClickListenerForEditComponents(editCardDate,"date");
        assignOnClickListenerForEditComponents(editCardCvc,"cvc");
        assignOnClickListenerForEditComponents(editCardPin,"pin");
        assignOnClickListenerForEditComponents(editCardNote,"note");

    }

    private void assignOnClickListenerForEditComponents(ImageButton button, final String forField) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (forField){
                    case "name":
                        enableFormEditComponents(cardName);
                        break;
                    case "number":
                        enableFormEditComponents(cardNumber);
                        break;
                    case "date":
                        enableFormEditComponents(cardExpiryDate);
                        break;
                    case "cvc":
                        enableFormEditComponents(cardCVC);
                        break;
                    case "pin":
                        enableFormEditComponents(cardPin);
                        break;
                    case "note":
                        enableFormEditComponents(cardNotes);
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
                        clipData=ClipData.newPlainText("",cardName.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(CardDetailActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "number":
                        clipData=ClipData.newPlainText("",cardNumber.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(CardDetailActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "date":
                        clipData=ClipData.newPlainText("",cardExpiryDate.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(CardDetailActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "cvc":
                        clipData=ClipData.newPlainText("",cardCVC.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(CardDetailActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "pin":
                        clipData=ClipData.newPlainText("",cardPin.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(CardDetailActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                    case "note":
                        clipData=ClipData.newPlainText("",cardNotes.getText());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(CardDetailActivity.this,"("+clipData.getItemAt(0).getText()+") Copied!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setVisibilityOfEditAndCopyComponents() {
        findViewById(R.id.relativeLayoutCardCopySectionName).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardCopySectionCvc).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardCopySectionDate).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardCopySectionNote).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardCopySectionNumber).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardCopySectionPin).setVisibility(View.VISIBLE);

        findViewById(R.id.relativeLayoutCardEditSectionName).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardEditSectionCvc).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardEditSectionDate).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardEditSectionNote).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardEditSectionNumber).setVisibility(View.VISIBLE);
        findViewById(R.id.relativeLayoutCardEditSectionPin).setVisibility(View.VISIBLE);
    }

    private void loadCardDetailsAndSetProperties(String objId) {
        HashMap<String, Card> allCards= CredvaultAuthenticationData.getAllCardAuthentication();
        cardModel= allCards.get(objId);
        cardName.setText(cardModel.getName());
        cardNumber.setText(cardModel.getNumber());
        cardExpiryDate.setText(cardModel.getExpiryDate());
        cardCVC.setText(cardModel.getCvc());
        cardPin.setText(cardModel.getPin());
        cardNotes.setText(cardModel.getAdditional());
        checkBoxFavourite.setChecked(cardModel.isFavorite());

        disableFormEditComponents(cardName);
        disableFormEditComponents(cardNumber);
        disableFormEditComponents(cardNotes);
        disableFormEditComponents(cardPin);
        disableFormEditComponents(cardCVC);
        disableFormEditComponents(cardExpiryDate);

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

    private void fillCardFromForm(Card cardModel) {
        if(cardModel.getId()==null) {
            cardModel.setId(databaseReference.push().getKey());
        }
        cardModel.setName(cardName.getText().toString());
        cardModel.setNumber(cardNumber.getText().toString());
        cardModel.setCvc(cardCVC.getText().toString());
        cardModel.setExpiryDate(cardExpiryDate.getText().toString());
        cardModel.setPin(cardPin.getText().toString());
        cardModel.setAdditional(cardNotes.getText().toString());
        cardModel.setFavorite(checkBoxFavourite.isChecked());
    }

    private void bindAllFormElements() {
        cardName= findViewById(R.id.editTextCardName);
        cardNumber= findViewById(R.id.editTextCardNumber);
        cardExpiryDate= findViewById(R.id.editTextCardDate);
        cardCVC = findViewById(R.id.editTextCardCvc);
        cardPin = findViewById(R.id.editTextCardPin);
        cardNotes= findViewById(R.id.editTextCardNote);
        checkBoxFavourite=findViewById(R.id.credCheckBoxFavCard);
        saveButton=findViewById(R.id.credSaveNewCard);
    }
}
