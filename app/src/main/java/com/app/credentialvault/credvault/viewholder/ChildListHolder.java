package com.app.credentialvault.credvault.viewholder;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.credentialvault.credvault.R;
import com.app.credentialvault.credvault.activities.CardDetailActivity;
import com.app.credentialvault.credvault.activities.HomeActivity;
import com.app.credentialvault.credvault.activities.NewWebsiteActivity;
import com.app.credentialvault.credvault.activities.NotesActivity;
import com.app.credentialvault.credvault.model.BasicLoginInfo;
import com.app.credentialvault.credvault.model.WebSiteAuth;
import com.app.credentialvault.credvault.utils.AuthenticationType;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by JHA on 6/25/2018.
 */

public class ChildListHolder extends ChildViewHolder {
    TextView childLabel;
    ImageView childFavicon;
    LinearLayout credLoutChild;
    String objRefId;
    AuthenticationType authenticationType;
    Context context;

    public ChildListHolder(View itemView) {
        super(itemView);
        childLabel=itemView.findViewById(R.id.child_label);
        childFavicon=itemView.findViewById(R.id.child_favicon);
        credLoutChild=itemView.findViewById(R.id.credLoutChild);
        credLoutChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                switch(authenticationType){
                    case BASIC:
                        intent= new Intent(context, BasicLoginInfo.class);
                        break;
                    case CARD:
                        intent=new Intent(context,CardDetailActivity.class);
                        break;
                    case NOTES:
                        intent=new Intent(context,NotesActivity.class);
                        break;
                    case WEBPAGE:
                        intent=new Intent(context, NewWebsiteActivity.class);
                        break;
                    default:
                        Toast.makeText(view.getContext(), "Default case initiated due to some problem", Toast.LENGTH_LONG).show();
                        intent=new Intent(context, HomeActivity.class);
                }
                intent.putExtra("refObjId", objRefId);
                context.startActivity(intent);
                Toast.makeText(view.getContext(), "child="+childLabel.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void setChild(String label, int icon, String objRefId, AuthenticationType authType, Context context) {
        childLabel.setText(label);
        childFavicon.setImageResource(icon);
        this.objRefId=objRefId;
        authenticationType=authType;
        this.context=context;
    }
}
