package com.thebigoceaan.smartagriculture.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.adapters.ProductDetailsAdapter;
import com.thebigoceaan.smartagriculture.databinding.FragmentHomeBinding;
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
    int totalItem,currentItem,scrollOutItem;
    ArrayList<Product> list = new ArrayList<>();
    private ProductDetailsAdapter.RecyclerViewClickListener listener;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //For Progress bar
        binding.spinKit.setVisibility(View.GONE);
        Sprite cubegrid = new CubeGrid();
        binding.spinKit.setIndeterminateDrawable(cubegrid);

        //for display drawer layout
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Objects.requireNonNull(mActionBar).setDisplayHomeAsUpEnabled(true);

        setOnClickListener();
        binding.recyclerViewProductDetails.setHasFixedSize(true);
        adapter = new ProductDetailsAdapter(getContext(),listener,list);
        adapter.notifyDataSetChanged();
        binding.recyclerViewProductDetails.setAdapter(adapter);

        //get instance
        auth = FirebaseAuth.getInstance();

        crud = new CrudProduct();
        loadData();
        binding.recyclerViewProductDetails.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isLoading=true;
                }
            }

            @Override
            public void onScrolled(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager manager = (GridLayoutManager)binding.recyclerViewProductDetails.getLayoutManager();
                currentItem=manager.getChildCount();
                totalItem = manager.getItemCount();
                scrollOutItem = manager.findFirstVisibleItemPosition();

                if(isLoading && (currentItem+scrollOutItem==totalItem) ){
                    isLoading=false;
                    loadData();
                }
            }
        });

        return root;
    }

    private void loadData() {
        binding.spinKit.setVisibility(View.VISIBLE);
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
                binding.spinKit.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
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