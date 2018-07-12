package com.app.credentialvault.credvault.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by JHA on 4/1/2018.
 */
@IgnoreExtraProperties
public class User implements Serializable {

    @PropertyName("user_id")
    private static String userId;

    @PropertyName("user_name")
    private static String userName;

    @PropertyName("master_password")
    private static String master;

    @PropertyName("data")
    private static UserInformations userInformations;

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        User.userId = userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getMaster() {
        return master;
    }

    public static void setMaster(String master) {
        User.master = master;
    }

    public static UserInformations getUserInformations() {
        return userInformations;
    }

    public static void setUserInformations(UserInformations userInformations) {
        User.userInformations = userInformations;
    }

    public User() {
    }
}
