package com.eudev.bloodbank.bloodbankeu.activity.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.activity.BloodRequestActivity;
import com.eudev.bloodbank.bloodbankeu.activity.activity.UserDetailsActivity;
import com.eudev.bloodbank.bloodbankeu.activity.interfaces.ItemClickListener;
import com.eudev.bloodbank.bloodbankeu.activity.model.BloodRequest;
import com.eudev.bloodbank.bloodbankeu.activity.model.Common;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.eudev.bloodbank.bloodbankeu.activity.viewholder.BloodRequestViewHolder;
import com.eudev.bloodbank.bloodbankeu.activity.viewholder.UserListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodRequestFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference ref;

    private RecyclerView recycler_view;
    private LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<BloodRequest, BloodRequestViewHolder> adapter;


    public BloodRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //init firebase
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Blood_Request");

        View view =inflater.inflate(R.layout.fragment_blood_request, container, false);


        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_blood_request);
        recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(layoutManager);


        retriveAllBloodRequest();


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent blood_request = new Intent(getContext(),BloodRequestActivity.class);
//                blood_request.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(blood_request);
            }
        });

        return view;
    }

    private void retriveAllBloodRequest() {

        adapter = new FirebaseRecyclerAdapter<BloodRequest,
                BloodRequestViewHolder>(BloodRequest.class,
                R.layout.blood_request_custom_design,
                BloodRequestViewHolder.class,
                ref.orderByChild("blood_group")) {

            @Override
            protected void populateViewHolder(BloodRequestViewHolder viewHolder, final BloodRequest model, final int position) {

                viewHolder.allDetails.setText(model.getQuantity()
                        +" Bag "+model.getBlood_group()
                        +" blood is required for a "
                        +model.getPatient_type()+" patient at "
                        +model.getDate()+","+model.getTime()+" in "
                        +model.getHospital_name()+" Hospital.");

                viewHolder.phone.setText("Contact With: \n"+model.getContact_no());

                viewHolder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                        Intent dialintnt = new Intent(Intent.ACTION_CALL, Uri.parse(("tel:" +model.getContact_no())));
                        startActivity(dialintnt);
                    }
                });


                final BloodRequest clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongPress) {


                        //Toast.makeText(HomeActivity.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getContext(), UserDetailsActivity.class);
//                        intent.putExtra("userId", adapter.getRef(pos).getKey());
//
//                        Log.i("morshed", "Click item Key:--"+adapter.getRef(pos).getKey());
//                        startActivity(intent);


                    }
                });


            }
        };
        recycler_view.setAdapter(adapter);

    }

}
