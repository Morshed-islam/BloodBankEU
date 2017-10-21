package com.eudev.bloodbank.bloodbankeu.activity.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.model.BloodRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;


public class BloodRequestActivity extends AppCompatActivity implements
        View.OnClickListener {


    private Button _blood_request_btn;
    private Spinner _blood_group;
    private EditText _quantity, _patient_type, _hospital_name, _area,_contact_no;
    private EditText datePicker, timePicker;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private android.app.AlertDialog progressDialog;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

       //init fireBase database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blood_Request");



        init();

        progressDialog = new SpotsDialog(this);

        timePicker.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        _blood_request_btn.setOnClickListener(this);


    }

    private void bloodRequestCalling() {


        if (!validate()) {
            onLoginFailed();
            return;
        }

        progressDialog.show();

        String quantity = _quantity.getText().toString();
        String patientType = _patient_type.getText().toString();
        String hospitalName = _hospital_name.getText().toString();
        String area = _area.getText().toString();
        String contactNo = _contact_no.getText().toString();
        String date = datePicker.getText().toString();
        String time = timePicker.getText().toString();

        String bloodGroup = String.valueOf(_blood_group.getSelectedItem());




        DatabaseReference bloodRequest = mDatabase.push();
        //BloodRequest bdr = new BloodRequest(bloodGroup,quantity,patientType,hospitalName,area,date,time);

        bloodRequest.child("blood_group").setValue(bloodGroup);
        bloodRequest.child("quantity").setValue(quantity);
        bloodRequest.child("patient_type").setValue(patientType);
        bloodRequest.child("hospital_name").setValue(hospitalName);
        bloodRequest.child("area").setValue(area);
        bloodRequest.child("contact_no").setValue(contactNo);
        bloodRequest.child("date").setValue(date);
        bloodRequest.child("time").setValue(time);

        progressDialog.dismiss();

        Toast.makeText(this, "Request Successfully!!", Toast.LENGTH_SHORT).show();
        Intent back = new Intent(getApplicationContext(),HomeActivity.class);
        //back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(back);


    }

    private void init() {

        _blood_request_btn = (Button) findViewById(R.id.blood_request_btn);
        _blood_group = (Spinner) findViewById(R.id.request_blood_group);
        _quantity = (EditText) findViewById(R.id.request_quantity);
        _patient_type = (EditText) findViewById(R.id.request_patient_type);
        _hospital_name = (EditText) findViewById(R.id.request_hospital_name);
        _area = (EditText) findViewById(R.id.request_area);
        _contact_no = (EditText) findViewById(R.id.request_phone);

        datePicker = (EditText) findViewById(R.id.date_picker);
        timePicker = (EditText) findViewById(R.id.time_picker);
    }


    @Override
    public void onClick(View v) {
        if (v == datePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            datePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == timePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            timePicker.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }


        if (v == _blood_request_btn) {
            bloodRequestCalling();



        }
    }



    private boolean validate() {

        boolean valid = true;

        if (_quantity.getText().toString().isEmpty()) {
            _quantity.setError("Required!!");
            valid = false;
        } else {
            _quantity.setError(null);
        }
        if (_patient_type.getText().toString().isEmpty()) {
            _patient_type.setError("Required!!");
            valid = false;
        } else {
            _patient_type.setError(null);
        }
        if (_hospital_name.getText().toString().isEmpty()) {
            _hospital_name.setError("Required!!");
            valid = false;
        } else {
            _hospital_name.setError(null);
        }
        if (_area.getText().toString().isEmpty()) {
            _area.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _area.setError(null);
        }

        if (String.valueOf(_blood_group.getSelectedItem()).toString().isEmpty()) {
            Toast.makeText(this, "Select Blood Group", Toast.LENGTH_SHORT).show();
            valid = false;
        }if (datePicker.getText().toString().isEmpty()) {
            datePicker.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            datePicker.setError(null);
        }if (timePicker.getText().toString().isEmpty()) {
            timePicker.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            timePicker.setError(null);
        }

        return valid;

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        _blood_request_btn.setEnabled(true);
    }



}
