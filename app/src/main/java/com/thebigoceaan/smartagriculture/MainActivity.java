package com.thebigoceaan.smartagriculture;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.thebigoceaan.smartagriculture.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set App bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4fb424"));
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends

        //get instance
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_connect, R.id.navigation_more,R.id.navigation_account)
                .build();

        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//for drawer layout
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,R.string.start,R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.navigationView.setNavigationItemSelectedListener(this);

        loginLogoutValidate();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken==null){
                    auth.signOut();
                }
            }
        };

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return false;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_agro_news:
                Toast.makeText(this, "This redirects to agro news activity fragments", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_daily_vegetable_market:
                Toast.makeText(this, "This redirects to daily vagetables market", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about_us:
                Toast.makeText(this, "This redirects to about us page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share_app:
                Toast.makeText(this, "This redirects to share app to any other playform", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                auth.signOut();
                LoginManager.getInstance().logOut();
                Toast.makeText(this, "Successfully Logout the account.", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }

    private void loginLogoutValidate() {
        MenuItem loginText = binding.navigationView.getMenu().findItem(R.id.nav_login);
        MenuItem logoutText = binding.navigationView.getMenu().findItem(R.id.nav_logout);
        if (auth.getCurrentUser() != null) {
            loginText.setVisible(false);
            logoutText.setVisible(true);
        } else {
            loginText.setVisible(true);
            logoutText.setVisible(false);
        }
    }
}