package com.thebigoceaan.smartagriculture.services.products;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.FarmerProfileAdapter;
import com.thebigoceaan.smartagriculture.adapters.OrderAdapter;
import com.thebigoceaan.smartagriculture.databinding.FragmentFarmerProfileBinding;
import com.thebigoceaan.smartagriculture.models.Order;
import com.thebigoceaan.smartagriculture.services.order.CrudOrder;
import com.thebigoceaan.smartagriculture.services.order.OrderList;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FarmerProfileFragment extends Fragment {
    FragmentFarmerProfileBinding binding;
    FarmerProfileAdapter adapter;
    ArrayList<Order> list = new ArrayList<>();
    boolean isLoading = false;
    CrudOrder crud = new CrudOrder();
    String key = null;
    FirebaseAuth auth ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFarmerProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        //firebase instance
        auth = FirebaseAuth.getInstance();


//setting recyclerview
        binding.recyclerViewFarmerProfile.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        binding.recyclerViewFarmerProfile.setLayoutManager(manager);
        adapter = new FarmerProfileAdapter(list);
        adapter.notifyDataSetChanged();
        binding.recyclerViewFarmerProfile.setAdapter(adapter);

        loadData();
        binding.recyclerViewFarmerProfile.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)binding.recyclerViewFarmerProfile.getLayoutManager();
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

        // Inflate the layout for this fragment
        return view;
    }

    private void loadData() {
        crud.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Order> order = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Order order1 = data.getValue(Order.class);
                    if (order1.getSellerEmail().equals(auth.getCurrentUser().getEmail())) {
                        order1.setKey(data.getKey());
                        order.add(order1);
                        key = data.getKey();
                    }

                    binding.swipFarmerProfile.setRefreshing(false);

                }
                adapter.setItem(order);
                adapter.notifyDataSetChanged();
                isLoading = false;

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                binding.swipFarmerProfile.setRefreshing(false);
                Toasty.error(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT,true).show();
            }
        });
    }
}