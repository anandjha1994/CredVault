package com.app.credentialvault.credvault.model;

import com.google.firebase.database.PropertyName;

/**
 * Created by JHA on 9/16/2018.
 */

public class Notes {

    @PropertyName("id")
    private String id;

    @PropertyName("title")
    private String title;

    @PropertyName("note")
    private String note;

    @PropertyName("favorite")
    private boolean favorite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
