package com.app.credentialvault.credvault.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by JHA on 4/1/2018.
 */
@IgnoreExtraProperties
public class Card {

    @PropertyName("card_number")
    private static String number;

    @PropertyName("expiry_date")
    private static String exDate;

    @PropertyName("cvv")
    private static String cvv;

    @PropertyName("user_name")
    private static String pin;


    public Card() {
        // Default Constructor
    }

    public static String getNumber() {
        return number;
    }

    public static void setNumber(String number) {
        Card.number = number;
    }

    public static String getExDate() {
        return exDate;
    }

    public static void setExDate(String exDate) {
        Card.exDate = exDate;
    }

    public static String getCvv() {
        return cvv;
    }

    public static void setCvv(String cvv) {
        Card.cvv = cvv;
    }

    public static String getPin() {
        return pin;
    }

    public static void setPin(String pin) {
        Card.pin = pin;
    }

}
