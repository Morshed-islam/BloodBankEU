package com.eudev.bloodbank.bloodbankeu.activity.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.interfaces.ItemClickListener;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.eudev.bloodbank.bloodbankeu.activity.viewholder.UserListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;

    private Button searchBtn;
    private Spinner searchBlood;


    private RecyclerView recycler_view;
    private LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<User, UserListViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Firebase init
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("User");


        //local variable init
        searchBtn = (Button) findViewById(R.id.search_donor);

        searchBlood = (Spinner) findViewById(R.id.search_blood_group);


        //recycler
        recycler_view = (RecyclerView) findViewById(R.id.search_rcv);
        recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(layoutManager);



        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  String department = String.valueOf(searchDept.getSelectedItem());
                String blood = String.valueOf(searchBlood.getSelectedItem());

                if (!blood.isEmpty()){
                    filteringBydptAndBlood(blood);

                }else {
                    Toast.makeText(getApplicationContext(), "Please Select Department!", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }

    private void filteringBydptAndBlood(String blood) {


        Query query = ref.orderByChild("blood_group").equalTo(blood);



        adapter = new FirebaseRecyclerAdapter<User, UserListViewHolder>(
                User.class,
                R.layout.user_custom_design,
                UserListViewHolder.class,
                query

        ) {
            @Override
            protected void populateViewHolder(UserListViewHolder viewHolder, final User model, final int position) {

                viewHolder.userName.setText(model.getName());
                viewHolder.phone.setText(model.getPhone());
                viewHolder.bloodGroup.setText(model.getBlood_group());

                viewHolder.actionCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//
                    //    Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
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
