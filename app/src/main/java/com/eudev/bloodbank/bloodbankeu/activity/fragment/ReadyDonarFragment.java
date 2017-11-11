package com.eudev.bloodbank.bloodbankeu.activity.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.activity.BloodRequestActivity;
import com.eudev.bloodbank.bloodbankeu.activity.activity.UserDetailsActivity;
import com.eudev.bloodbank.bloodbankeu.activity.interfaces.ItemClickListener;
import com.eudev.bloodbank.bloodbankeu.activity.model.Common;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.eudev.bloodbank.bloodbankeu.activity.model.UserReady;
import com.eudev.bloodbank.bloodbankeu.activity.viewholder.UserListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

    FloatingActionButton fab;

    private FirebaseDatabase database;
    private DatabaseReference ref;

    private RecyclerView recycler_user;
    private LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<User, UserListViewHolder> adapter;


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
        ref = database.getReference("User");


        View view = inflater.inflate(R.layout.fragment_ready_donar, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_ready);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReadyDialog();

            }
        });


        //recycler view
        recycler_user = (RecyclerView) view.findViewById(R.id.recycler_ready_donor);
        recycler_user.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_user.setLayoutManager(layoutManager);


        //String blood = Common.currentUser.getBlood_group();

        checkIFUserIsReady();


        // Inflate the layout for this fragment
        return view;
    }

    private void showReadyDialog() {

         Bundle dBundle = null;


        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater(dBundle);

        final View v = inflater.inflate(R.layout.custom_dialog, null);
        dialog.setView(v);

        final Button _done_btn = (Button) v.findViewById(R.id.done_btn);
        final Spinner _readyDonor = (Spinner) v.findViewById(R.id.ready_donor);


        dialog.setTitle("Ready Donor");

        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();




        _done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (String.valueOf(_readyDonor.getSelectedItem()).isEmpty()){

                   // updateReadyDonorValue();
                    Toast.makeText(getContext(), "Please Select Yes/No", Toast.LENGTH_SHORT).show();

                }else{

                    String key = Common.currentUser.getPhone();

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(key);

                    String val = String.valueOf(_readyDonor.getSelectedItem());
                    mDatabase.child("ready").setValue(val);


                    Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();

                    alertDialog.dismiss();
                }


            }
        });


    }


    private void checkIFUserIsReady() {


//        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
//        Query query = mFirebaseDatabaseReference.child("User").orderByChild("ready").equalTo("A+");
//
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    //TODO get the data here
//
//                    System.out.println(postSnapshot.toString() + "\n");
//                    Log.i("morshed", postSnapshot.toString());
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        query.addValueEventListener(valueEventListener);




        adapter = new FirebaseRecyclerAdapter<User, UserListViewHolder>(User.class,
                R.layout.user_custom_design,
                UserListViewHolder.class,
                ref.orderByChild("ready").equalTo("Yes")) {
            @Override
            protected void populateViewHolder(UserListViewHolder viewHolder, final User model, final int position) {

                viewHolder.userName.setText(model.getName());
                viewHolder.bloodGroup.setText("Blood: "+model.getBlood_group());
                viewHolder.phone.setText("+88"+model.getPhone());
                //viewHolder.phoneNumber.;

                viewHolder.actionCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                        Intent dialintnt = new Intent(Intent.ACTION_CALL, Uri.parse(("tel:" +model.getPhone())));
                        startActivity(dialintnt);
                    }
                });


                final User clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongPress) {


                        //Toast.makeText(HomeActivity.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getContext(), UserDetailsActivity.class);
//                        intent.putExtra("userId", adapter.getRef(pos).getKey());
//
//                        Log.i("morshed", "Click item Key:--"+adapter.getRef(pos).getKey());
//                        startActivity(intent);
//

                    }
                });


            }
        };
        recycler_user.setAdapter(adapter);


    }


    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

}
