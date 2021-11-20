package com.thebigoceaan.smartagriculture.dashboard.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.ConnectAdapter;
import com.thebigoceaan.smartagriculture.adapters.InfoAdapter;
import com.thebigoceaan.smartagriculture.dashboard.info.CrudInfo;
import com.thebigoceaan.smartagriculture.databinding.ActivityViewConnectBinding;
import com.thebigoceaan.smartagriculture.models.Connect;
import com.thebigoceaan.smartagriculture.models.Info;

import java.util.ArrayList;

public class ViewConnectActivity extends AppCompatActivity {
    ActivityViewConnectBinding binding;
    ConnectAdapter adapter;
    CrudConnect crud;
    boolean isLoading=false;
    String key=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewConnectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //for action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4fb424"));
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends

        binding.recyclerViewConnect.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        binding.recyclerViewConnect.setLayoutManager(manager);
        adapter = new ConnectAdapter(this);
        adapter.notifyDataSetChanged();
        binding.recyclerViewConnect.setAdapter(adapter);

        crud = new CrudConnect();

        binding.recyclerViewConnect.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)binding.recyclerViewConnect.getLayoutManager();
                int totalItem=linearLayoutManager.getItemCount();
                int lastVisible=linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItem<lastVisible+3){
                    if(!isLoading){
                        isLoading=true;
                        loadData();
                    }

                }
            }
        });


    }
    private void loadData() {
        binding.swipViewConnect.setRefreshing(true);
        crud.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Connect> connect = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Connect connect1 = data.getValue(Connect.class);
                    connect1.setKey(data.getKey());
                    connect.add(connect1);
                    key = data.getKey();
                }
                adapter.setItem(connect);
                adapter.notifyDataSetChanged();
                isLoading = false;
                binding.swipViewConnect.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                binding.swipViewConnect.setRefreshing(false);
            }
        });
    }
}