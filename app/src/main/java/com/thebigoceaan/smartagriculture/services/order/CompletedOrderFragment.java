package com.thebigoceaan.smartagriculture.services.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.FarmerProfileAdapter;
import com.thebigoceaan.smartagriculture.adapters.NewsViewAdapter;
import com.thebigoceaan.smartagriculture.databinding.FragmentCompletedOrderBinding;
import com.thebigoceaan.smartagriculture.models.News;
import com.thebigoceaan.smartagriculture.models.Order;

import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;


public class CompletedOrderFragment extends Fragment {
    FragmentCompletedOrderBinding binding;
    FarmerProfileAdapter adapter;
    FarmerProfileAdapter.OnClickFarmerProfile listener;
    ArrayList<Order> list = new ArrayList<>();
    private boolean isLoading = false;
    private String key = null;
    private CrudOrder crud = new CrudOrder();
    private FirebaseAuth auth;
    private DatabaseReference database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletedOrderBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        //instance
        database = FirebaseDatabase.getInstance().getReference("News");

        binding.swipeCompletedOrders.setRefreshing(true);
        //calling set adapter method
        binding.completedOrdersRv.hasFixedSize();
        //Reverse RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.completedOrdersRv.setLayoutManager(linearLayoutManager);
        adapter= new FarmerProfileAdapter(list,listener);
        binding.completedOrdersRv.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();

        crud.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Order order1 = dataSnapshot.getValue(Order.class);
                    if(order1.isCompleted() && order1.getSellerEmail().equals(auth.getCurrentUser().getEmail())){
                        order1.setKey(dataSnapshot.getKey());
                        list.add(order1);
                        key = dataSnapshot.getKey();
                    }
                }
                binding.swipeCompletedOrders.setRefreshing(false);
                adapter.setItem(list);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}