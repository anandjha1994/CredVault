package com.app.credentialvault.credvault.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JHA on 4/1/2018.
 */
@IgnoreExtraProperties
public class UserInformations implements Serializable {

    @PropertyName("basic_login")
    private static List<BasicLoginInfo> basicLoginInfo;

    @PropertyName("card")
    private static List<Card> card;

    @PropertyName("website")
    private static List<WebSiteAuth> webSiteAuth;

    public static List<BasicLoginInfo> getBasicLoginInfo() {
        return basicLoginInfo;
    }

    public static void setBasicLoginInfo(List<BasicLoginInfo> basicLoginInfo) {
        UserInformations.basicLoginInfo = basicLoginInfo;
    }

    public static List<Card> getCard() {
        return card;
    }

    public static void setCard(List<Card> card) {
        UserInformations.card = card;
    }

    public static List<WebSiteAuth> getWebSiteAuth() {
        return webSiteAuth;
    }

    public static void setWebSiteAuth(List<WebSiteAuth> webSiteAuth) {
        UserInformations.webSiteAuth = webSiteAuth;
    }

    public UserInformations() {
    }

}
