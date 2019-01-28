package com.app.credentialvault.credvault.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by JHA on 4/1/2018.
 */
public class WebSiteAuth{
    @PropertyName("id")
    private String id;

    @PropertyName("name")
    private String name;

    @PropertyName("url")
    private String url;

    @PropertyName("username")
    private String userName;

    @PropertyName("password")
    private String password;


    @PropertyName("additional")
    private String additional;


    @PropertyName("favorite")
    private boolean favorite;

    public WebSiteAuth() {
        //Default constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
