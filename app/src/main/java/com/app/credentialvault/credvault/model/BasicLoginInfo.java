package com.app.credentialvault.credvault.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by JHA on 4/1/2018.
 */
@IgnoreExtraProperties
public class BasicLoginInfo {

    @PropertyName("id")
    private String id;

    @PropertyName("userName")
    private String userName;

    @PropertyName("name")
    private String name;

    @PropertyName("email")
    private String email;

    @PropertyName("password")
    private String password;

    @PropertyName("additional")
    private String additional;

    @PropertyName("isFavourite")
    private String isFavourite;


    public BasicLoginInfo() {
        //Default
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }


}
