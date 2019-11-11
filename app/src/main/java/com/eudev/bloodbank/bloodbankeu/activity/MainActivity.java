package com.eudev.bloodbank.bloodbankeu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.login.LoginActivity;
import com.eudev.bloodbank.bloodbankeu.activity.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private Button signIn,signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });


    }


}
