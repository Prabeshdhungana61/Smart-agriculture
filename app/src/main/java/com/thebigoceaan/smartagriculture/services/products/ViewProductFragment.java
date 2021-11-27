package com.thebigoceaan.smartagriculture.services.products;

import android.app.backup.BackupDataInput;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.adapters.ProductAdapter;
import com.thebigoceaan.smartagriculture.databinding.FragmentViewProductBinding;
import com.thebigoceaan.smartagriculture.models.Product;
import java.util.ArrayList;

public class ViewProductFragment extends Fragment {
    FragmentViewProductBinding binding;
    ProductAdapter adapter;
    boolean isLoading=false;
    CrudProduct crud;
    String key=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewProductBinding.inflate(inflater,container,false);
        View root = binding.getRoot();


        binding.recyclerViewProduct.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        binding.recyclerViewProduct.setLayoutManager(manager);
        adapter = new ProductAdapter(getContext());
        adapter.notifyDataSetChanged();
        binding.recyclerViewProduct.setAdapter(adapter);

        crud = new CrudProduct();
        loadData();
        binding.recyclerViewProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)binding.recyclerViewProduct.getLayoutManager();
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

        return root;
    }
    private void loadData() {
        binding.shimmerText.startShimmer();
        crud.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Product> product = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Product product1 = data.getValue(Product.class);
                    product1.setKey(data.getKey());
                    product.add(product1);
                    key = data.getKey();
                }
                adapter.setItem(product);
                adapter.notifyDataSetChanged();
                isLoading = false;
                binding.shimmerText.stopShimmer();
                binding.shimmerText.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                binding.shimmerText.stopShimmer();
            }
        });
    }

}