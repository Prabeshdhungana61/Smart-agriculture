package com.thebigoceaan.smartagriculture.services.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.OrderAdapter;
import com.thebigoceaan.smartagriculture.databinding.ActivityOrderListBinding;
import com.thebigoceaan.smartagriculture.hyperlink.DailyVegMarketActivity;
import com.thebigoceaan.smartagriculture.models.Order;
import java.util.ArrayList;
import es.dmoral.toasty.Toasty;

public class OrderList extends AppCompatActivity {
    ActivityOrderListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set App bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable drawable =new ColorDrawable(ContextCompat.getColor(this, R.color.splashColor));
        actionBar.setBackgroundDrawable(drawable); //action bar ends

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("PENDING ORDER", PendingOrderListFragment.class)
                .add("COMPLETED ORDER", CompletedOrdersListFragment.class)
                .create());

        ViewPager viewPager =  findViewById(R.id.viewpagerOrderList);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab =  findViewById(R.id.viewpagerTabOrderList);
        viewPagerTab.setViewPager(viewPager);


    }
}