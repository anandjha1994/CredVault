package com.app.credentialvault.credvault.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by JHA on 6/25/2018.
 */

public class Child implements Parcelable{

    private String label;

    private int iconResId;

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public Child(String label, int iconResId) {
        this.label = label;
        this.iconResId=iconResId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static final Creator<Child> CREATOR = new Creator<Child>() {
        @Override
        public Child[] newArray(int i) {
            return new Child[0];
        }

        @Override
        public Child createFromParcel(Parcel in) {
            return new Child(in);
        }
    };
        protected Child(Parcel in) {
            label = in.readString();
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
