package com.thebigoceaan.smartagriculture.services;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.ActivityNewsBinding;

public class NewsActivity extends AppCompatActivity {
    ActivityNewsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set App bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4fb424"));
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends


    }
}