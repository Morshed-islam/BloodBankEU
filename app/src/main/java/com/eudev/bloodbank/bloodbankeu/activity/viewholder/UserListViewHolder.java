package com.eudev.bloodbank.bloodbankeu.activity.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.interfaces.ItemClickListener;

/**
 * Created by Morshed on 10/19/2017.
 */

public class UserListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView userName;
    public TextView bloodGroup;
    public TextView phone;
    public ImageButton actionCall;


    private ItemClickListener itemClickListener;

    public UserListViewHolder(View itemView) {
        super(itemView);


        userName =  (TextView) itemView.findViewById(R.id.user_name_tv);
        bloodGroup =  (TextView) itemView.findViewById(R.id.user_blood_group);
        phone =  (TextView) itemView.findViewById(R.id.user_phone);
        actionCall =  (ImageButton) itemView.findViewById(R.id.call_action_btn);


        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
