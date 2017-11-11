package com.eudev.bloodbank.bloodbankeu.activity.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.activity.UserDetailsActivity;
import com.eudev.bloodbank.bloodbankeu.activity.interfaces.ItemClickListener;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.eudev.bloodbank.bloodbankeu.activity.viewholder.UserListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllDonarFragment extends Fragment {

    private String callPhone;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private RecyclerView recycler_user;
    private LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<User, UserListViewHolder> adapter;
    FirebaseRecyclerAdapter<User, UserListViewHolder> mAdapter;

    public AllDonarFragment() {
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
        ref = database.getReference("User");

        View view = inflater.inflate(R.layout.fragment_all_donar, container, false);
        ;


        recycler_user = (RecyclerView) view.findViewById(R.id.recycler_user);
        recycler_user.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_user.setLayoutManager(layoutManager);

        // retriveAllData();

        loadAllDonar();


        return view;
    }

    private void loadAllDonar() {

        adapter = new FirebaseRecyclerAdapter<User, UserListViewHolder>(User.class, R.layout.user_custom_design, UserListViewHolder.class, ref.orderByChild("name")) {
            @Override
            protected void populateViewHolder(UserListViewHolder viewHolder, final User model, final int position) {

                viewHolder.userName.setText(model.getName());
                viewHolder.bloodGroup.setText("Blood: " + model.getBlood_group());
                viewHolder.phone.setText("+88" + model.getPhone());
                //viewHolder.phoneNumber.;


                callPhone = model.getPhone();

                viewHolder.actionCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                            call_action();


//                        Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
//                        Intent dialintnt = new Intent(Intent.ACTION_CALL, Uri.parse(("tel:" + model.getPhone())));
//                        startActivity(dialintnt);
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


                    }
                });


            }
        };
        recycler_user.setAdapter(adapter);

    }



   /* private void retriveAllData() {

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);

                System.out.println("\n"+user.getPhone()+"\n"+user.getName()+"\n");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/


   //TODO --------------------phone call permission ----------------

//    public boolean isPermissionGranted() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v("TAG", "Permission is granted");
//                return true;
//            } else {
//
//                Log.v("TAG", "Permission is revoked");
//                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
//                return false;
//            }
//        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v("TAG", "Permission is granted");
//            return true;
//        }
//    }
//
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[],
//                                           int[] grantResults) {
//        switch (requestCode) {
//
//            case 1: {
//
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
//                    call_action();
//                } else {
//                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }


    public void call_action(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + callPhone));
        startActivity(callIntent);
    }



}




/*
    void checkIfPlayerIsUser(final String phoneNum) {
        DatabaseReference mDatabaseReference =
                FirebaseDatabase.getInstance()
                .getReference().child("users");

        if (!TextUtils.isEmpty(phoneNum)) {
            final Query phoneNumReference = mDatabaseReference.orderByChild("phoneNum").equalTo(phoneNum);

            ValueEventListener phoneNumValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Toast.makeText(getApplicationContext(), "****PLAYER FOUND****", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "****NOT FOUND****", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };

            phoneNumReference.addListenerForSingleValueEvent(phoneNumValueEventListener);


        } else {
            Log.e("Error","phoneNum is null");
        }
    }*/


