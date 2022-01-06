package com.thebigoceaan.smartagriculture.services.products;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.SlideBottom;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.thebigoceaan.smartagriculture.models.Sale;
import com.thebigoceaan.smartagriculture.services.order.CrudOrder;
import com.thebigoceaan.smartagriculture.services.order.CrudSale;
import com.thebigoceaan.smartagriculture.services.order.OrderList;

import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class FarmerProfileFragment extends Fragment {
    FragmentFarmerProfileBinding binding;
    FarmerProfileAdapter adapter;
    ArrayList<Order> list = new ArrayList<>();
    boolean isLoading = false;
    CrudOrder crud = new CrudOrder();
    String key = null;
    FirebaseAuth auth ;
    private FarmerProfileAdapter.OnClickFarmerProfile listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFarmerProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        //firebase instance
        auth = FirebaseAuth.getInstance();

        setOnClickRadioButton();

//setting recyclerview
        binding.recyclerViewFarmerProfile.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        binding.recyclerViewFarmerProfile.setLayoutManager(manager);
        adapter = new FarmerProfileAdapter(list,listener);
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

    private void setOnClickRadioButton(){
        listener=(view,position)->{
            Order order = list.get(position);

            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(getContext());
            dialogBuilder.withTitle("ARE YOU SURE ?")
                    .withMessage("Are you sure this order is completed ? This process is cannot be undone later.")
                    .withButton1Text("COMPLETED")
                    .withButton2Text("CANCEL")
                    .withMessageColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.white))
                    .withTitleColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.white))
                    .withDuration(300)
                    .withEffect(SlideBottom)
                    .withIcon(R.drawable.ic_error_outline_white_24dp)
                    .withDialogColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.darkGreen))
                    .setButton2Click(view1 -> {
                        dialogBuilder.dismiss();
                        order.setCompleted(true);
                    })
                    .setButton1Click(view12 -> {
                        dialogBuilder.dismiss();
                        adapter.notifyDataSetChanged();
                        Sale sale = new Sale();
                        CrudSale sale2 = new CrudSale();
                        int totalPrice = Integer.parseInt(order.getOrderPrice());
                        int orders = list.size();
                        sale.setTotalSale(totalPrice);
                        sale.setTotalOrders(orders);
                        sale2.add(sale).addOnSuccessListener(unused -> {
                            Toasty.success(getContext(), "Successfully completed the order ", Toasty.LENGTH_SHORT,true).show();
                        }).addOnFailureListener(e -> {
                            Toasty.error(getContext(), ""+e.getMessage(), Toasty.LENGTH_SHORT,true).show();
                        });
                    })
                    .show();

        };
    }
}