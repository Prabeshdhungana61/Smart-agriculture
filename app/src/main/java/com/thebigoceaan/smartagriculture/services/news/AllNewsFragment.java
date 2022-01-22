package com.thebigoceaan.smartagriculture.services.news;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.NewsViewAdapter;
import com.thebigoceaan.smartagriculture.dashboard.news.CrudNews;
import com.thebigoceaan.smartagriculture.databinding.FragmentAllNewsBinding;
import com.thebigoceaan.smartagriculture.models.News;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class AllNewsFragment extends Fragment {
    FragmentAllNewsBinding binding;
    private NewsViewAdapter.RecyclerViewClickListener listener;
    private NewsViewAdapter adapter;
    private boolean isLoading=false;
    private CrudNews crud;
    private String key=null;
    FirebaseAuth auth;
    DatabaseReference database;
    private final ArrayList<News> list = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllNewsBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        //instance
        database = FirebaseDatabase.getInstance().getReference("News");
        //swip layout call
        setOnClickListener();
        binding.swipAllNews.setRefreshing(true);
        //calling set adapter method
        binding.recyclerViewAllNews.hasFixedSize();
        //Reverse RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.recyclerViewAllNews.setLayoutManager(linearLayoutManager);
        NewsViewAdapter newsAdapter= new NewsViewAdapter(getContext(),listener,list);
        binding.recyclerViewAllNews.setAdapter(newsAdapter);
        //get instance
        auth = FirebaseAuth.getInstance();

        database.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
           binding.swipAllNews.setRefreshing(false);
           for(DataSnapshot dataSnapshot : snapshot.getChildren()){
               News newsList = dataSnapshot.getValue(News.class);
               list.add(newsList);

           }
           newsAdapter.notifyDataSetChanged();
       }

       @Override
       public void onCancelled(@NonNull @NotNull DatabaseError error) {
           Log.d("AllNewsFragment",error.getMessage()+"");
       }});

        return view;
    }
    private void setOnClickListener(){
        listener=(view,position)->{
            Intent intent = new Intent(getContext(),NewsDetailsActivity.class);
            intent.putExtra("NEWS_SOURCE", list.get(position).getNews_source());
            intent.putExtra("LINK", list.get(position).getNews_link());
            startActivity(intent);
        };
    }
}