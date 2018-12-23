package com.app.credentialvault.credvault.model;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.List;

/**
 * Created by JHA on 10/1/2018.
 */

public class CredvaultAuthenticationData {

    private static HashMap<String, BasicLoginInfo> allBasicAuthentication;

    private static HashMap<String, Card> allCardAuthentication;

    private static HashMap<String, WebSiteAuth> allWebpageAuthentication;

    private static HashMap<String, Notes> allNotesAuthentication;

    private static HashMap<String, BasicLoginInfo> allFavBasicAuthentication;

    private static HashMap<String, Card> allFavCardAuthentication;

    private static HashMap<String, WebSiteAuth> allFavWebpageAuthentication;

    private static HashMap<String, Notes> allFavNotesAuthentication;



    public  static void setAllBasicAuthentication(HashMap<String ,BasicLoginInfo> allBasicAuth) {
        CredvaultAuthenticationData.allBasicAuthentication = allBasicAuth;
    }

    public static void setAllCardAuthentication(HashMap<String, Card> allCardAuthentication) {
        CredvaultAuthenticationData.allCardAuthentication = allCardAuthentication;
    }

    public static void setAllWebpageAuthentication(HashMap<String, WebSiteAuth> allWebpageAuthentication) {
        CredvaultAuthenticationData.allWebpageAuthentication = allWebpageAuthentication;
    }

    public static void setAllNotesAuthentication(HashMap<String, Notes> allNotesAuthentication) {
        CredvaultAuthenticationData.allNotesAuthentication = allNotesAuthentication;
    }


    public static void setAllFavBasicAuthentication(HashMap<String, BasicLoginInfo> allFavBasicAuthentication) {
        CredvaultAuthenticationData.allFavBasicAuthentication = allFavBasicAuthentication;
    }

    public static void setAllFavCardAuthentication(HashMap<String, Card> allFavCardAuthentication) {
        CredvaultAuthenticationData.allFavCardAuthentication = allFavCardAuthentication;
    }

    public static void setAllFavWebpageAuthentication(HashMap<String, WebSiteAuth> allFavWebpageAuthentication) {
        CredvaultAuthenticationData.allFavWebpageAuthentication = allFavWebpageAuthentication;
    }

    public static void setAllFavNotesAuthentication(HashMap<String, Notes> allFavNotesAuthentication) {
        CredvaultAuthenticationData.allFavNotesAuthentication = allFavNotesAuthentication;
    }

    @Contract(pure = true)
    public static HashMap<String, BasicLoginInfo> getAllBasicAuthentication() {
        return allBasicAuthentication;
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static HashMap<String, Card> getAllCardAuthentication() {
        return allCardAuthentication;
    }

    @Contract(pure = true)
    public static HashMap<String, WebSiteAuth> getAllWebpageAuthentication() {
        return allWebpageAuthentication;
    }

    @Contract(pure = true)
    public static HashMap<String, Notes> getAllNotesAuthentication() {
        return allNotesAuthentication;
    }

    @Contract(pure = true)
    public static HashMap<String, BasicLoginInfo> getAllFavBasicAuthentication() {
        return allFavBasicAuthentication;
    }

    @Contract(pure = true)
    public static HashMap<String, Card> getAllFavCardAuthentication() {
        return allFavCardAuthentication;
    }

    @Contract(pure = true)
    public static HashMap<String, WebSiteAuth> getAllFavWebpageAuthentication() {
        return allFavWebpageAuthentication;
    }

    @Contract(pure = true)
    public static HashMap<String, Notes> getAllFavNotesAuthentication() {
        return allFavNotesAuthentication;
    }

}
