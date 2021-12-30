package com.thebigoceaan.smartagriculture.services.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.OrderAdapter;
import com.thebigoceaan.smartagriculture.adapters.ProductAdapter;
import com.thebigoceaan.smartagriculture.databinding.ActivityOrderListBinding;
import com.thebigoceaan.smartagriculture.models.Order;
import com.thebigoceaan.smartagriculture.models.Product;
import com.thebigoceaan.smartagriculture.services.products.CrudProduct;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class OrderList extends AppCompatActivity {
    ActivityOrderListBinding binding;
    private OrderAdapter adapter;
    private ArrayList<Order> list = new ArrayList<Order>();
    private boolean isLoading=false;
    private CrudOrder crud = new CrudOrder();
    String key = null;
    private FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();
        binding.swipOrder.setRefreshing(true);

        binding.recyclerViewOrderList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        binding.recyclerViewOrderList.setLayoutManager(manager);
        adapter = new OrderAdapter(this,list);
        adapter.notifyDataSetChanged();
        binding.recyclerViewOrderList.setAdapter(adapter);

        loadData();
        binding.recyclerViewOrderList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)binding.recyclerViewOrderList.getLayoutManager();
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
        crud.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Order> order = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Order order1 = data.getValue(Order.class);
                    if (order1.getBuyerEmail().equals(auth.getCurrentUser().getEmail())) {
                        order1.setKey(data.getKey());
                        order.add(order1);
                        key = data.getKey();
                    }

                    binding.swipOrder.setRefreshing(false);

                }
                adapter.setItem(order);
                adapter.notifyDataSetChanged();
                isLoading = false;

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                binding.swipOrder.setRefreshing(false);
                Toasty.error(OrderList.this, "" + error.getMessage(), Toast.LENGTH_SHORT,true).show();
            }
        });
    }
}