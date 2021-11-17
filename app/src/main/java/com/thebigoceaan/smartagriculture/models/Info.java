package com.thebigoceaan.smartagriculture.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Info implements Serializable {
    @Exclude
    private String key;
    String infoId, infoImage, infoTitle,  infoDetails;
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Info(String infoId, String infoImage, String infoTitle, String infoDetails) {
        this.infoId = infoId;
        this.infoImage = infoImage;
        this.infoTitle = infoTitle;
        this.infoDetails = infoDetails;
    }
    public Info(String infoImage, String infoTitle, String infoDetails ){
        if (infoTitle.trim().equals("") && infoDetails.equals("")) {

            infoTitle="No Title";
            infoDetails= "No Details";
        }
        this.infoImage = infoImage;
        this.infoTitle = infoTitle;
        this.infoDetails = infoDetails;
    }
    public Info(String infoTitle, String infoDetails ){
        this.infoTitle = infoTitle;
        this.infoDetails = infoDetails;
    }
     Info(){ }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoImage() {
        return infoImage;
    }

    public void setInfoImage(String infoImage) {
        this.infoImage = infoImage;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoDetails() {
        return infoDetails;
    }

    public void setInfoDetails(String infoDetails) {
        this.infoDetails = infoDetails;
    }
}
