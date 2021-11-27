package com.thebigoceaan.smartagriculture.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Farmer implements Serializable {
    @Exclude
    private String key;

    String mobile,district, municipality,userid;

    public Farmer(String mobile, String district, String municipality,String userid) {
        this.mobile = mobile;
        this.district = district;
        this.municipality = municipality;
        this.userid = userid;
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
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
