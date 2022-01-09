package com.thebigoceaan.smartagriculture.services.products;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.SlideBottom;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.adapters.FarmerProfileAdapter;
import com.thebigoceaan.smartagriculture.adapters.OrderAdapter;
import com.thebigoceaan.smartagriculture.databinding.FragmentFarmerProfileBinding;
import com.thebigoceaan.smartagriculture.models.Farmer;
import com.thebigoceaan.smartagriculture.models.Order;
import com.thebigoceaan.smartagriculture.models.Sale;
import com.thebigoceaan.smartagriculture.services.order.CrudOrder;
import com.thebigoceaan.smartagriculture.services.order.CrudSale;
import com.thebigoceaan.smartagriculture.services.order.OrderDashboard;
import com.thebigoceaan.smartagriculture.services.order.OrderList;
import com.thebigoceaan.smartagriculture.services.register.CrudFarmer;
import com.thebigoceaan.smartagriculture.services.register.FarmerRegisterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class FarmerProfileFragment extends Fragment {
    FragmentFarmerProfileBinding binding;
    CrudFarmer crudFarmer = new CrudFarmer();
    private FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFarmerProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        //firebase instance
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();

        farmerDashboard();//farmer dashboard method call
        farmerProfile();//farmer profile method call

        // Inflate the layout for this fragment
        return view;
    }

    private void farmerProfile(){
        Glide.with(getContext()).load(auth.getCurrentUser().getPhotoUrl()).into(binding.farmerProfilePicture);
        binding.farmerUsernameTextView.setText(auth.getCurrentUser().getDisplayName());
        binding.editFarmerProfileButton.setOnClickListener(view -> {
            //set intent to edit profile page
            database.getReference("Farmer").child(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Farmer farmer = dataSnapshot.getValue(Farmer.class);
                    Intent intent = new Intent(getContext(), FarmerRegisterActivity.class);
                    intent.putExtra("EDITFARMER",farmer);
                    startActivity(intent);
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            });

        });
    }

    private void farmerDashboard(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database.getReference("Sale").child(user.getUid()).get().addOnSuccessListener(dataSnapshot -> {
            Sale sale = dataSnapshot.getValue(Sale.class);
            binding.totalSaleTextView.setText(sale!=null ? sale.getTotalSale() +"Rs" : "0 Rs");
            binding.totalOrderTextView.setText(sale!=null?sale.getTotalOrders()+"":"0");
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        });

        binding.orderDashboardButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), OrderDashboard.class);
            startActivity(intent);
        });

    }

}