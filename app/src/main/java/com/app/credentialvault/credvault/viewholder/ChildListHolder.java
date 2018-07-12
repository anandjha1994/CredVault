package com.app.credentialvault.credvault.viewholder;

import android.media.Image;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.activities.HomeActivity;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by JHA on 6/25/2018.
 */

public class ChildListHolder extends ChildViewHolder {
    TextView childLable;
    ImageView childFavicon;
    CardView cardView;

    public ChildListHolder(View itemView) {
        super(itemView);
        childLable=(TextView) itemView.findViewById(R.id.child_label);
        childFavicon=(ImageView) itemView.findViewById(R.id.child_favicon);
        cardView=(CardView) itemView.findViewById(R.id.childCardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "child="+childLable.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setChild(String lable,int icon) {
        childLable.setText(lable);
        childFavicon.setImageResource(icon);
    }
}
