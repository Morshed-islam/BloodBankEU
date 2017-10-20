package com.eudev.bloodbank.bloodbankeu.activity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.model.Common;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.eudev.bloodbank.bloodbankeu.activity.model.UserReady;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadyDonarFragment extends Fragment {


    private FirebaseDatabase database;
    private DatabaseReference table_user_ready;
    private DatabaseReference table_user;


    public ReadyDonarFragment() {
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


        //init firebase
        database = FirebaseDatabase.getInstance();
        table_user_ready = database.getReference("User_Ready/phone");
        table_user = database.getReference("User");

        View view = inflater.inflate(R.layout.fragment_ready_donar, container, false);


        String blood = Common.currentUser.getBlood_group();

        checkIFUserIsReady(blood);


        // Inflate the layout for this fragment
        return view;
    }


    private void checkIFUserIsReady(final String blood) {

       table_user.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               if (dataSnapshot.child(blood.toString()).equals("A+"));



               Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }
}
