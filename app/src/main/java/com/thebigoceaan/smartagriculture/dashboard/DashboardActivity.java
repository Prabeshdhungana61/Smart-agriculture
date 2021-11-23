package com.thebigoceaan.smartagriculture.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.thebigoceaan.smartagriculture.LoginActivity;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.dashboard.info.AddInfoActivity;
import com.thebigoceaan.smartagriculture.dashboard.info.ViewInfoActivity;
import com.thebigoceaan.smartagriculture.dashboard.news.AddNewsActivity;
import com.thebigoceaan.smartagriculture.dashboard.news.ViewNewsActivity;
import com.thebigoceaan.smartagriculture.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        //action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4fb424"));
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends

        binding.imgAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AddNewsActivity.class);
                startActivity(intent);
            }
        });
        binding.imgViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ViewNewsActivity.class);
                startActivity(intent);
            }
        });
        binding.imgViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ViewInfoActivity.class);
                startActivity(intent);
            }
        });
        binding.imgAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AddInfoActivity.class);
                startActivity(intent);
            }
        });
        binding.imgViewMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AddInfoActivity.class);
                startActivity(intent);
            }
        });
        binding.imgViewMsg2.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_signout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_signout) {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Successfully Logout the admin account.", Toast.LENGTH_SHORT).show();
            auth.signOut();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}