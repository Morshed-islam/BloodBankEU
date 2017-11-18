package com.eudev.bloodbank.bloodbankeu.activity.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.model.Common;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class EditProfileActivity extends AppCompatActivity {

    private EditText _update_name, _update_phone,
            _update_varsity_id, _update_password;

    private Spinner _update_department, _update_blood_group, _update_ready;
    private Button _update_btn;

    private DatabaseReference user_table;
    private android.app.AlertDialog proAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init firebase
        user_table = FirebaseDatabase.getInstance().getReference().child("User");

        //init all instant
        init();

        //alert
        proAlertDialog = new SpotsDialog(this);




        //data retrive to set value in all edittext
        retriveDataToSetExistingValue();

        _update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                proAlertDialog.show();
                String name = _update_name.getText().toString();
                String phone = _update_phone.getText().toString();
                String varsityId = _update_varsity_id.getText().toString();
                String password = _update_password.getText().toString();

                String bloodGroup = String.valueOf(_update_blood_group.getSelectedItem());
                String department = String.valueOf(_update_department.getSelectedItem());
                String ready = String.valueOf(_update_ready.getSelectedItem());







//
                if (!validate()) {
                    proAlertDialog.dismiss();
                    return;

                } else {

                    updateCurrentUser(name, phone, varsityId, password, bloodGroup, department, ready);
                    Toast.makeText(EditProfileActivity.this, "Update Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    proAlertDialog.dismiss();
                }

            }
        });




    }

    private void updateCurrentUser(String name, String phone, String varsityId, String pass, String blood, String department, String ready) {

//        String key = mDatabase.child("posts").push().getKey();
//        Post post = new Post(userId, username, title, body);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//        mDatabase.updateChildren(childUpdates);



        String key = Common.currentUser.getPhone();

        User user = new User(name, phone, varsityId, pass, blood, department,ready);

        Map<String, Object> userValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, userValues);


        user_table.updateChildren(childUpdates);


    }


    private boolean validate() {

        boolean valid = true;

        if (_update_name.getText().toString().isEmpty()) {
            _update_name.setError("Required!!");
            valid = false;
        } else {
            _update_name.setError(null);
        }
        if (_update_phone.getText().toString().isEmpty()) {
            _update_phone.setError("Required!!");
            valid = false;
        } else {
            _update_phone.setError(null);
        }
        if (_update_varsity_id.getText().toString().isEmpty()) {
            _update_varsity_id.setError("Required!!");
            valid = false;
        } else {
            _update_varsity_id.setError(null);
        }
        if (String.valueOf(_update_blood_group.getSelectedItem()).toString().isEmpty()
                && String.valueOf(_update_department.getSelectedItem()).toString().isEmpty()
                && String.valueOf(_update_ready.getSelectedItem()).toString().isEmpty()) {
            Toast.makeText(this, "Plz select Blood!,Department & Ready Donor ", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(_update_password.getText().toString().isEmpty() || _update_password.getText().toString().length() < 4 || _update_password.getText().toString().length() > 10){
            _update_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }else {

            _update_password.setError(null);
        }


        return valid;

    }


    private void retriveDataToSetExistingValue() {

        String UID = Common.currentUser.getPhone();

        user_table.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.i("morshed", user.getName());
                _update_name.setText(user.getName());
                _update_phone.setText(user.getPhone());
                _update_password.setText(user.getPassword());
                _update_varsity_id.setText(user.getVarsity_id());

                setSpinText(_update_blood_group,user.getBlood_group());
                setSpinText(_update_department,user.getDepartment());
                setSpinText(_update_ready,user.getReady());

              //  String dptArray[] = getResources().getStringArray(R.array.department);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }


    private void init() {
        _update_name = (EditText) findViewById(R.id.update_input_name);
        _update_phone = (EditText) findViewById(R.id.update_input_phone);
        _update_varsity_id = (EditText) findViewById(R.id.update_input_id);
        _update_password = (EditText) findViewById(R.id.update_input_password);

        _update_blood_group = (Spinner) findViewById(R.id.update_bloodgroup);
        _update_department = (Spinner) findViewById(R.id.update_department);
        _update_ready = (Spinner) findViewById(R.id.update_ready);
        _update_btn = (Button) findViewById(R.id.update_btn);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


}




