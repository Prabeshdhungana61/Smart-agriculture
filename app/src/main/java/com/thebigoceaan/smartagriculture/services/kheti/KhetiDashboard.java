package com.thebigoceaan.smartagriculture.services.kheti;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.Utilities;
import com.thebigoceaan.smartagriculture.databinding.ActivityKhetiDashboardBinding;
import com.thebigoceaan.smartagriculture.services.products.AddProductFragment;
import com.thebigoceaan.smartagriculture.services.products.FarmerProfileFragment;
import com.thebigoceaan.smartagriculture.services.products.ViewProductFragment;

public class KhetiDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kheti_dashboard);

        //action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        Utilities.appBarColor(actionBar,this);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("फलफुल खेती", PhalPhulKhetiFragment.class)
                .add("जडिबुटी खेती", JadibutiKhetiFragment.class)
                .add("तरकारी खेती", TarkariKhetiFragment.class)
                .create());
        ViewPager viewPager =  findViewById(R.id.viewpagerKheti);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab =  findViewById(R.id.viewpagertabKheti);
        viewPagerTab.setViewPager(viewPager);
    }
}