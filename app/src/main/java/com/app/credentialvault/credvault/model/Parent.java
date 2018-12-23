package com.app.credentialvault.credvault.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by JHA on 6/25/2018.
 */

public class Parent  extends ExpandableGroup <Child> {

    private int iconResId;

    private int totalChild;

    public Parent(String title, List items, int iconResId,int childCount) {
        super(title, items);
        this.iconResId=iconResId;
        this.totalChild=childCount;
    }

    public  String getTotalChild(){
        return String.valueOf(totalChild);
    }

    public int getIconResId() {
        return iconResId;
    }
}
