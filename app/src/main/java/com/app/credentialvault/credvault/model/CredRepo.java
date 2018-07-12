package com.app.credentialvault.credvault.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JHA on 3/24/2018.
 */

public class CredRepo implements Serializable {

    private static int id;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        CredRepo.id = id;
    }

    private static List<User> users;

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        CredRepo.users = users;
    }

    public CredRepo() {
    }
}
