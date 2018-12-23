package com.app.credentialvault.credvault.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.model.Parent;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by JHA on 6/25/2018.
 */

public class ParentViewHolder extends GroupViewHolder{
    private TextView parentLabel;
    private TextView childCount;
    private ImageView arrow;
    private ImageView icon;

    public ParentViewHolder(View itemView) {
        super(itemView);
        parentLabel =  itemView.findViewById(R.id.parent_text_label);
        childCount =  itemView.findViewById(R.id.parent_text_count);
        arrow = itemView.findViewById(R.id.parent_icon_tail);
        icon =  itemView.findViewById(R.id.parent_icon_head);
    }

    public void setParent(ExpandableGroup parent){
        parentLabel.setText(parent.getTitle());
        icon.setBackgroundResource(((Parent) parent).getIconResId());
        childCount.setText(((Parent) parent).getTotalChild());
    }
}
