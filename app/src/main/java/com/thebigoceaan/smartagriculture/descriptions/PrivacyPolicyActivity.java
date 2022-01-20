package com.thebigoceaan.smartagriculture.descriptions;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.Utilities;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        // Set App bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        Utilities.appBarColor(actionBar,this); //action bar ends
    }
}