package com.eudev.bloodbank.bloodbankeu.activity.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.eudev.bloodbank.bloodbankeu.activity.register.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class AddNewMemberActivity extends AppCompatActivity {

    private EditText _name, _phone, _id;
    private Spinner _department, _blood_group,_ready_donor;

    private Button _addBtn;

    private android.app.AlertDialog progressDialog;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Member");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init fireBase database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");

        init();

        progressDialog = new SpotsDialog(this);


        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUerAddCalling();
            }
        });
    }

    private void newUerAddCalling() {

        if (!validate()) {

            return;
        }

        progressDialog.show();

//        String name = _name.getText().toString();
//        String phone = _phone.getText().toString();
//        String id = _id.getText().toString();
//
//        String blood = String.valueOf(_blood_group.getSelectedItem());
//        String department = String.valueOf(_department.getSelectedItem());
//
//
//        DatabaseReference  ref = mDatabase.child(phone);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(_phone.getText().toString()).exists()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Phone number already Exists", Toast.LENGTH_SHORT).show();
                    Log.i("morshed", "if condition call in Signup Activity");
                } else {

                    String name = _name.getText().toString();
                    String phone = _phone.getText().toString();
                    String id = _id.getText().toString();

                    String blood = String.valueOf(_blood_group.getSelectedItem());
                    String department = String.valueOf(_department.getSelectedItem());
                    String ready = String.valueOf(_ready_donor.getSelectedItem());


                    progressDialog.dismiss();
                    Log.i("morshed", "Else condition call in Signup Activity");
                    User user = new User(name, phone,id,blood,department,ready);
                    mDatabase.child(phone).setValue(user);
                    Toast.makeText(getApplicationContext(), "Added Successfully!!", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void init() {

        _name = (EditText) findViewById(R.id.new_member_input_name);
        _id = (EditText) findViewById(R.id.new_member_input_id);
        _phone = (EditText) findViewById(R.id.new_member_input_phone);

        _blood_group = (Spinner) findViewById(R.id.new_member_bloodgroup);
        _department = (Spinner) findViewById(R.id.new_member_department);
        _ready_donor = (Spinner) findViewById(R.id.new_member_ready);


        _addBtn = (Button) findViewById(R.id.new_member_btn);
    }


    private boolean validate() {

        boolean valid = true;

        if (_name.getText().toString().isEmpty()) {
            _name.setError("Required!!");
            valid = false;
        } else {
            _name.setError(null);
        }
        if (_id.getText().toString().isEmpty()) {
            _id.setError("Required!!");
            valid = false;
        } else {
            _id.setError(null);
        }
        if (_phone.getText().toString().isEmpty()) {
            _phone.setError("Required!!");
            valid = false;
        } else {
            _phone.setError(null);
        }
        if (String.valueOf(_blood_group.getSelectedItem()).toString().isEmpty()
                && String.valueOf(_department.getSelectedItem()).toString().isEmpty()
                && String.valueOf(_ready_donor.getSelectedItem()).toString().isEmpty()) {
            Toast.makeText(this, "Plz select Blood!,Department & Ready Donor ", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }



}
