package com.eudev.bloodbank.bloodbankeu.activity.model;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Morshed on 10/20/2017.
 */

public class UserReady{
    private String ready;

    public UserReady() {
    }

    public UserReady(String ready) {
        this.ready = ready;
    }

    public String getReady() {
        return ready;
    }

    public void setReady(String ready) {
        this.ready = ready;
    }
}
