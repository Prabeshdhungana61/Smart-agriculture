package com.thebigoceaan.smartagriculture.descriptions.help;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.Utilities;
import com.thebigoceaan.smartagriculture.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {
    ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initial fragment added
        UserLoginHelpFragment fragment = new UserLoginHelpFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frame_container, fragment).commit();

        //appbar
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        Utilities.appBarColor(actionBar,this);
    }
}