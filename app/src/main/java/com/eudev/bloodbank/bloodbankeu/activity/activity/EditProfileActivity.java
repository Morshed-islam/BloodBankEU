package com.eudev.bloodbank.bloodbankeu.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.model.Common;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private EditText _update_name, _update_phone,
            _update_varsity_id, _update_password;

    private Spinner _update_department, _update_blood_group, _update_ready;
    private Button _update_btn;

    private DatabaseReference user_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //init firebase
        user_table = FirebaseDatabase.getInstance().getReference().child("User");

        //init all instant
        init();

        //data retrive to set value in all edittext
        retriveDataToSetExistingValue();

        _update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = _update_name.getText().toString();
                String phone = _update_phone.getText().toString();
                String varsityId = _update_varsity_id.getText().toString();
                String password = _update_password.getText().toString();

                String bloodGroup = String.valueOf(_update_blood_group.getSelectedItem());
                String department = String.valueOf(_update_department.getSelectedItem());
                String ready = String.valueOf(_update_ready.getSelectedItem());


                if (bloodGroup.isEmpty() && department.isEmpty() && ready.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Plz select blood group,department & Ready", Toast.LENGTH_SHORT).show();

                } else {

                    updateCurrentUser(name, phone, varsityId, password, bloodGroup, department, ready);
                    Toast.makeText(EditProfileActivity.this, "Update Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
}




