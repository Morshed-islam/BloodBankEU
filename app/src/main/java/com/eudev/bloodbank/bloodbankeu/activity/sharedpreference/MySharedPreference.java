package com.eudev.bloodbank.bloodbankeu.activity.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Morshed on 11/13/2017.
 */

public class MySharedPreference {

    private static MySharedPreference mySharedPreference;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private MySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static MySharedPreference getPreferences(Context context) {
        if (mySharedPreference == null) mySharedPreference = new MySharedPreference(context);
        return mySharedPreference;
    }



    public void setUserPhoneNumber(String phoneNumber){
        editor.putString(Config.PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    public String getUserPhoneNumber(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(Config.PHONE_NUMBER, "");
    }


    public void setPassword(String password){
        editor.putString(Config.PASSWORD, password);
        editor.apply();
    }

    public String getPassword(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(Config.PASSWORD, "");
    }



    public void setLoginFlag(boolean isStudent){
        editor.putBoolean(Config.IS_LOGIN, isStudent);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(Config.IS_LOGIN, false); //assume the default value is false
    }

}