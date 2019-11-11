package com.eudev.bloodbank.bloodbankeu.activity.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.interfaces.ItemClickListener;
import com.eudev.bloodbank.bloodbankeu.activity.model.BloodRequest;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.eudev.bloodbank.bloodbankeu.activity.viewholder.BloodRequestViewHolder;
import com.eudev.bloodbank.bloodbankeu.activity.viewholder.UserListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference table_user;

    private Spinner departmentSpinner;
    private ImageButton departmentBtn;

    private RecyclerView recycler_view;
    private LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<User, UserListViewHolder> adapter;



    public DepartmentFragment() {
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
        View view =inflater.inflate(R.layout.fragment_department, container, false);

        //init fireBase
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference().child("User");

        departmentSpinner = (Spinner) view.findViewById(R.id.department_spinner);
        departmentBtn = (ImageButton)view.findViewById(R.id.department_search_btn);



        //recycler
        recycler_view = (RecyclerView) view.findViewById(R.id.department_rcv);
        recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(layoutManager);



        //For first time show the data without action
        departmentSpinner.setSelection(1);
        retrivedManupulateData("CSE");
        departmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String department = String.valueOf(departmentSpinner.getSelectedItem());

                if (!department.isEmpty()){
                    retrivedManupulateData(department);

                }else {
                    Toast.makeText(getContext(), "Please Select Department!", Toast.LENGTH_SHORT).show();
                }


            }
        });




        return view;
    }

    private void retrivedManupulateData(String department) {

        adapter = new FirebaseRecyclerAdapter<User,
                UserListViewHolder>(User.class,
                R.layout.user_custom_design,
                UserListViewHolder.class,
                table_user.orderByChild("department").equalTo(department)
                //table_user.orderByChild("blood_group").equalTo(department)
                ) {


            @Override
            protected void populateViewHolder(UserListViewHolder viewHolder, final User model, final int position) {

                viewHolder.userName.setText(model.getName());
                viewHolder.phone.setText(model.getPhone());
                viewHolder.bloodGroup.setText(model.getBlood_group());


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


                    }
                });



            }
        };
        recycler_view.setAdapter(adapter);

    }


}
