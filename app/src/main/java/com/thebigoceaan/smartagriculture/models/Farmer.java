package com.thebigoceaan.smartagriculture.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Farmer implements Serializable {
    @Exclude
    private String key;

    String mobile,district, Municipality;

    public Farmer(String mobile, String district, String municipality) {
        this.mobile = mobile;
        this.district = district;
        Municipality = municipality;
    }
    public Farmer(){}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMunicipality() {
        return Municipality;
    }

    public void setMunicipality(String municipality) {
        Municipality = municipality;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
