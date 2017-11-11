package com.eudev.bloodbank.bloodbankeu.activity.register;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.login.LoginActivity;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = "signUp";
    private EditText _nameText, _phoneText, _vasityIdText, _passwordText;
    private Button _signUpButton;

    private Spinner _bloodGroup, _department;
    private TextView _backToLogin;

    private FirebaseDatabase database;
    private DatabaseReference table_user;
    private android.app.AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");


        progressDialog = new SpotsDialog(this);
        init();


        _signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        backToLogin();

    }


    private void signUp() {

        Log.i(TAG, "SignUP");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        progressDialog.show();


        table_user.addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(_phoneText.getText().toString()).exists()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Phone number already Exists", Toast.LENGTH_SHORT).show();
                    Log.i(TAG + "phone number Exists-", "if condition call in Signup Activity");
                }
                else
                {
                    String name = _nameText.getText().toString();
                    String phone = _phoneText.getText().toString();
                    String varsityId = _vasityIdText.getText().toString();
                    String password = _passwordText.getText().toString();

                    String bloodGroup = String.valueOf(_bloodGroup.getSelectedItem());
                    String department = String.valueOf(_department.getSelectedItem());


                    progressDialog.dismiss();
                    Log.i(TAG + "phone number Exists-", "Else condition call in Signup Activity");
                    User user = new User(name, phone, varsityId, password, bloodGroup, department);
                    table_user.child(phone).setValue(user);
                    Toast.makeText(RegisterActivity.this, "SignUp Successfully!!", Toast.LENGTH_SHORT).show();
                    finish();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        _signUpButton.setEnabled(true);
    }


    private boolean validate() {

        boolean valid = true;

        if (_phoneText.getText().toString().isEmpty() || !Patterns.PHONE.matcher(_phoneText.getText().toString()).matches()) {
            _phoneText.setError("enter a valid phone number");
            valid = false;
        } else {
            _phoneText.setError(null);
        }
        if (_nameText.getText().toString().isEmpty()) {
            _nameText.setError("Enter your name");
            valid = false;
        } else {
            _nameText.setError(null);
        }
        if (_vasityIdText.getText().toString().isEmpty()) {
            _vasityIdText.setError("Enter valid ID");
            valid = false;
        } else {
            _vasityIdText.setError(null);
        }
        if (_passwordText.getText().toString().isEmpty() || _passwordText.getText().toString().length() < 4 || _passwordText.getText().toString().length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (String.valueOf(_bloodGroup.getSelectedItem()).toString().isEmpty()
                && String.valueOf(_department.getSelectedItem()).toString().isEmpty()) {
            Toast.makeText(this, "Select Blood Group & Department", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {

        }

        return valid;

    }

    private void init() {


        _nameText = (EditText) findViewById(R.id.signup_input_name);
        _phoneText = (EditText) findViewById(R.id.signup_input_phone);
        _vasityIdText = (EditText) findViewById(R.id.signup_input_id);
        _passwordText = (EditText) findViewById(R.id.signup_input_password);

        _backToLogin = (TextView) findViewById(R.id.link_login);
        _signUpButton = (Button) findViewById(R.id.btn_signUp);

        _bloodGroup = (Spinner) findViewById(R.id.signup_bloodgroup);
        _department = (Spinner) findViewById(R.id.signup_department);


    }


    private void backToLogin() {

        _backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


}