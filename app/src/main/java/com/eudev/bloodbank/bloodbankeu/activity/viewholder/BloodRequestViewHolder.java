package com.eudev.bloodbank.bloodbankeu.activity.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.interfaces.ItemClickListener;

/**
 * Created by Morshed on 10/20/2017.
 */

public class BloodRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView allDetails;
    public TextView phone;
    public ImageButton call;



    private ItemClickListener itemClickListener;

    public BloodRequestViewHolder(View itemView) {
        super(itemView);

        allDetails = (TextView) itemView.findViewById(R.id.all_details);
        phone = (TextView) itemView.findViewById(R.id.user_phone);
        call = (ImageButton) itemView.findViewById(R.id.action_btn);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
