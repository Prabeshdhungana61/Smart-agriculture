package com.thebigoceaan.smartagriculture.services.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.OrderAdapter;
import com.thebigoceaan.smartagriculture.databinding.ActivityOrderListBinding;
import com.thebigoceaan.smartagriculture.hyperlink.DailyVegMarketActivity;
import com.thebigoceaan.smartagriculture.models.Order;
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
    private OrderAdapter.RecyclerViewClickListener listener;
    Dialog dialog;

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

        setOnClickListenerOrder();

        auth = FirebaseAuth.getInstance();
        binding.swipOrder.setRefreshing(true);

        binding.recyclerViewOrderList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        binding.recyclerViewOrderList.setLayoutManager(manager);
        adapter = new OrderAdapter(this,list,listener);
        adapter.notifyDataSetChanged();
        binding.recyclerViewOrderList.setAdapter(adapter);

        dialog = new Dialog(this);

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

    public void setOnClickListenerOrder(){
        listener=(view,position)->{
          Order order = list.get(position);
          dialog.setContentView(R.layout.dialog_confirmation);
          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button yes,no;
            yes = dialog.findViewById(R.id.btn_yes_confirm);
            no = dialog.findViewById(R.id.btn_no_confirm);
            dialog.show();
            yes.setOnClickListener(view1 -> {
                crud.remove(order.getKey()).addOnSuccessListener(unused -> {
                    dialog.dismiss();
                    Intent intent = new Intent(this, OrderList.class);
                    startActivity(intent);
                    Toasty.success(OrderList.this, "Successfully removed the order from the list!", Toasty.LENGTH_SHORT,true).show();
                }).addOnFailureListener(e -> Toasty.error(OrderList.this, ""+e.getMessage(), Toasty.LENGTH_SHORT,true).show());
            });
            no.setOnClickListener(view12 -> {
                dialog.dismiss();
            });
        };
    }
}