package com.app.credentialvault.credvault.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by JHA on 4/1/2018.
 */
@IgnoreExtraProperties
public class Card{

    @PropertyName("id")
    private String id;

    @PropertyName("name_on_card")
    private String name;


    @PropertyName("card_number")
    private String number;

    @PropertyName("expiry_date")
    private String expiryDate;

    @PropertyName("cvc")
    private String cvc;

    @PropertyName("sec_pin")
    private String pin;

    @PropertyName("additional")
    private String additional;

    @PropertyName("favorite")
    private boolean favorite;

    public Card() {
        // Default Constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String exDate) {
        this.expiryDate = exDate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
