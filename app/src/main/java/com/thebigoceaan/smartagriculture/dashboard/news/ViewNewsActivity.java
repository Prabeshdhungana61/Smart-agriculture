package com.thebigoceaan.smartagriculture.dashboard.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.Utilities;
import com.thebigoceaan.smartagriculture.adapters.NewsAdapter;
import com.thebigoceaan.smartagriculture.databinding.ActivityViewNewsBinding;
import com.thebigoceaan.smartagriculture.models.News;

import java.util.ArrayList;

public class ViewNewsActivity extends AppCompatActivity {
    ActivityViewNewsBinding binding;
    NewsAdapter adapter;
    boolean isLoading=false;
    CrudNews crud;
    String key=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //for action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        Utilities.appBarColor(actionBar,this); //action bar ends

        binding.recyclerViewViewNews.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        binding.recyclerViewViewNews.setLayoutManager(manager);
        adapter = new NewsAdapter(this);
        adapter.notifyDataSetChanged();
        binding.recyclerViewViewNews.setAdapter(adapter);

        crud = new CrudNews();
        loadData();
        binding.recyclerViewViewNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)binding.recyclerViewViewNews.getLayoutManager();
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
        binding.swipViewNews.setRefreshing(true);
        crud.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<News> news = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    News news1 = data.getValue(News.class);
                    news1.setKey(data.getKey());
                    news.add(news1);
                    key=data.getKey();
                }
                adapter.setItem(news);
                adapter.notifyDataSetChanged();
                isLoading=false;
                binding.swipViewNews.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                binding.swipViewNews.setRefreshing(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        super.onBackPressed();
    }
}