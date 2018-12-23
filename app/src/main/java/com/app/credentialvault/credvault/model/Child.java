package com.app.credentialvault.credvault.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.app.credentialvault.credvault.utils.AuthenticationType;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by JHA on 6/25/2018.
 */

public class Child implements Parcelable{

    private String label;

    private int iconResId;

    private String objRefId;

    private AuthenticationType authType;

    private Context context;

    public Child(Context context,String label, int iconResId, String objRefId, AuthenticationType authType) {
        this.label = label;
        this.iconResId=iconResId;
        this.objRefId=objRefId;
        this.authType=authType;
        this.context=context;
    }

    public String getLabel() {
        return label;
    }

    public int getIconResId() {
        return iconResId;
    }

    public Context getContext() {
        return context;
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

    public String getObjRefId() {
        return objRefId;
    }

    public AuthenticationType getAuthType() {
        return authType;
    }
}
