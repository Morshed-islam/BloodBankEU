package com.eudev.bloodbank.bloodbankeu.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.activity.HomeActivity;
import com.eudev.bloodbank.bloodbankeu.activity.model.Common;
import com.eudev.bloodbank.bloodbankeu.activity.model.User;
import com.eudev.bloodbank.bloodbankeu.activity.register.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText _phoneText;
    private EditText _passwordText;
    private Button _loginButton;
    private Button _signupLink;

    private FirebaseDatabase database;
    private DatabaseReference table_user;
    //AlertDialog progressDialog;
    android.app.AlertDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        progressDialog = new SpotsDialog(this);

        init();

        //----------------Login Access---------------
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        //--------------Going to Register Activity--------------
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Register activity
               // Toast.makeText(getApplicationContext(), "valll", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
//                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }


    public void login() {
        Log.i(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        //alert dialog
        progressDialog.show();

        final String phone = _phoneText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        table_user.addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(phone).exists()) {
                    progressDialog.dismiss();
                    User user = dataSnapshot.child(phone).getValue(User.class);

                    Log.i(TAG + "onDataChange  User ---", "" + _phoneText.getText().toString());
                    Log.i(TAG + "onDataChange  User ---", "" + user.getPassword());


                    if (user.getPassword().equals(password)) {

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        Common.currentUser = user;
                        startActivity(intent);
                        finish();

                    } else {

                        progressDialog.dismiss();
                        Log.i(TAG + "onDataChange  inside else statement ---", "" + user);
                        Toast.makeText(getApplicationContext(), "Wrong Pass", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data not Exists", Toast.LENGTH_SHORT).show();

                }

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i(TAG + "onCanceled , Error is Here---", "" + databaseError);

            }
        });


//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        onLoginFailed();
//                        //progressDialog.dismiss();
//                    }
//                }, 3000);


        _loginButton.setEnabled(true);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            _phoneText.setError("enter a valid phone number");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void init() {

        _phoneText = (EditText) findViewById(R.id.login_input_phone);
        _passwordText = (EditText) findViewById(R.id.login_input_password);
        _loginButton = (Button) findViewById(R.id.btn_signin);
        _signupLink = (Button) findViewById(R.id.btn_signUp_login);
    }

}
