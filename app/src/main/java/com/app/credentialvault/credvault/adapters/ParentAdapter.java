package com.app.credentialvault.credvault.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.Child;
import com.app.credentialvault.credvault.viewholder.ChildListHolder;
import com.app.credentialvault.credvault.viewholder.ParentViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by JHA on 6/25/2018.
 */

public class ParentAdapter extends ExpandableRecyclerViewAdapter<ParentViewHolder, ChildListHolder> {

    public ParentAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_list_parent_main_menu,parent,false);
    return new ParentViewHolder(view);
    }

    @Override
    public ChildListHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_list_child_main_menu,parent,false);
        return new ChildListHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildListHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Child child= (Child) group.getItems().get(childIndex);
        holder.setChild(child.getLabel(),child.getIconResId(), child.getObjRefId(),child.getAuthType(),child.getContext());
    }

    @Override
    public void onBindGroupViewHolder(ParentViewHolder holder, int flatPosition, ExpandableGroup group) {
       holder.setParent(group);
    }

}
