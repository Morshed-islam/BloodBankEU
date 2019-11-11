package com.eudev.bloodbank.bloodbankeu.activity.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    int SPLASH_DISPLAY_LENGTH = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
               // overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);



    }

}
