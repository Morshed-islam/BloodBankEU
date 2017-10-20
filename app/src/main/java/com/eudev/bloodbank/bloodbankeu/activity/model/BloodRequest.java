package com.eudev.bloodbank.bloodbankeu.activity.model;

/**
 * Created by Morshed on 10/20/2017.
 */

public class BloodRequest {

    private String blood_group,quantity,patient_type,hospital_name,area,date,time,contact_no;

    public BloodRequest() {
    }

    public BloodRequest(String blood_group, String quantity, String patient_type, String hospital_name, String area, String date, String time, String contact_no) {
        this.blood_group = blood_group;
        this.quantity = quantity;
        this.patient_type = patient_type;
        this.hospital_name = hospital_name;
        this.area = area;
        this.date = date;
        this.time = time;
        this.contact_no = contact_no;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPatient_type() {
        return patient_type;
    }

    public void setPatient_type(String patient_type) {
        this.patient_type = patient_type;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
