package com.eudev.bloodbank.bloodbankeu.activity.model;

/**
 * Created by Morshed on 10/17/2017.
 */

public class User {

    private String name, phone,varsity_id, password, blood_group,department;

    public User() {
    }

    public User(String name, String phone, String varsity_id, String password, String blood_group, String department) {
        this.name = name;
        this.phone = phone;
        this.varsity_id = varsity_id;
        this.password = password;
        this.blood_group = blood_group;
        this.department = department;
    }

    public User(String name, String phone, String varsity_id, String blood_group, String department) {
        this.name = name;
        this.phone = phone;
        this.varsity_id = varsity_id;
        this.blood_group = blood_group;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVarsity_id() {
        return varsity_id;
    }

    public void setVarsity_id(String varsity_id) {
        this.varsity_id = varsity_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
