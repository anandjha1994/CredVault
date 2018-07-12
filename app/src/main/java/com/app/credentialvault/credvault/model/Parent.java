package com.app.credentialvault.credvault.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by JHA on 6/25/2018.
 */

public class Parent  extends ExpandableGroup <Child> {

    private int iconResId;

    public Parent(String title, List items, int iconResId) {
        super(title, items);
        this.iconResId=iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
