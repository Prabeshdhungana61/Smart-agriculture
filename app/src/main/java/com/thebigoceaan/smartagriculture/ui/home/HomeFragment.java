package com.thebigoceaan.smartagriculture.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.ProductAdapter;
import com.thebigoceaan.smartagriculture.adapters.ProductDetailsAdapter;
import com.thebigoceaan.smartagriculture.databinding.FragmentHomeBinding;
import com.thebigoceaan.smartagriculture.databinding.FragmentViewProductBinding;
import com.thebigoceaan.smartagriculture.models.Product;
import com.thebigoceaan.smartagriculture.services.products.CrudProduct;
import com.thebigoceaan.smartagriculture.services.products.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ProductDetailsAdapter adapter;
    boolean isLoading=false;
    CrudProduct crud;
    String key=null;
    FirebaseAuth auth;
    ArrayList<Product> list = new ArrayList<>();
    private ProductDetailsAdapter.RecyclerViewClickListener listener;
    private ShimmerFrameLayout shimmertext;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //for display drawer layout
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Objects.requireNonNull(mActionBar).setDisplayHomeAsUpEnabled(true);

        setOnClickListener();
        binding.recyclerViewProductDetails.setHasFixedSize(true);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        manager.setReverseLayout(true);
//        manager.setStackFromEnd(true);
//        binding.recyclerViewProductDetails.setLayoutManager(manager);
        adapter = new ProductDetailsAdapter(getContext(),listener,list);
        adapter.notifyDataSetChanged();
        binding.recyclerViewProductDetails.setAdapter(adapter);

        //get instance
        auth = FirebaseAuth.getInstance();

        crud = new CrudProduct();
        loadData();
        binding.recyclerViewProductDetails.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)binding.recyclerViewProductDetails.getLayoutManager();
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

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                shimmertext.stopShimmer();
                shimmertext.setVisibility(View.GONE);
                Toasty.error(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT,true).show();
            };
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setOnClickListener(){
        listener = (view,position) ->{
            Product product = list.get(position);
            Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
            intent.putExtra("TitleProductText",product.getProductTitle());
            intent.putExtra("StockProductText",product.getProductStock());
            intent.putExtra("ProductPriceText",product.getProductPrice());
            intent.putExtra("ProductImage",product.getProductImage());
            intent.putExtra("ProductDescText",product.getProductDescription());
            getContext().startActivity(intent);
        };
    }
}