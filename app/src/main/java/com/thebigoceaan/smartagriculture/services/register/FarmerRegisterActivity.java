package com.thebigoceaan.smartagriculture.services.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.ActivityFarmerRegisterBinding;
import com.thebigoceaan.smartagriculture.models.Farmer;
import com.thebigoceaan.smartagriculture.models.News;
import com.thebigoceaan.smartagriculture.models.Product;
import com.thebigoceaan.smartagriculture.services.products.ProductDashboard;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class FarmerRegisterActivity extends AppCompatActivity {
    ActivityFarmerRegisterBinding binding;
    private FirebaseAuth auth;
    private CrudFarmer crudFarmer = new CrudFarmer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFarmerRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4fb424"));
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends

        //get instance
        auth = FirebaseAuth.getInstance();

        //get data for edit or update
        Farmer farmer_edit= (Farmer) this.getIntent().getSerializableExtra("EDITFARMER");

        if(farmer_edit!=null){
            binding.btnRegFarmer.setText("UPDATE FARMER PROFILE");
            binding.districtEditText.setText(farmer_edit.getDistrict());
            binding.munEditText.setText(farmer_edit.getMunicipality());
            binding.mblNumEditText.setText(farmer_edit.getMobile());
        }
        else{
            binding.btnRegFarmer.setText(R.string.reg_farmer);
        }

        binding.btnRegFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(farmer_edit==null) {
                    Farmer farmer = new Farmer();
                    farmer.setDistrict(binding.districtEditText.getText().toString().trim());
                    farmer.setMunicipality(binding.munEditText.getText().toString().trim());
                    farmer.setMobile(binding.mblNumEditText.getText().toString().trim());
                    farmer.setUserid(auth.getCurrentUser().getUid());
                    if (auth.getCurrentUser() != null) {
                        CrudFarmer crud = new CrudFarmer();
                        crud.add(farmer).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                binding.munEditText.setText("");
                                binding.districtEditText.setText("");
                                binding.mblNumEditText.setText("");
                                Toasty.success(FarmerRegisterActivity.this, "Successfully registered as farmer", Toast.LENGTH_SHORT, true).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toasty.error(FarmerRegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            }
                        });

                    } else {
                        Toasty.warning(FarmerRegisterActivity.this, "Kindly Login first to register as a farmer", Toast.LENGTH_SHORT, true).show();
                    }
                }
                else{
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("district",binding.districtEditText.getText().toString());
                    hashMap.put("mobile",binding.mblNumEditText.getText().toString());
                    hashMap.put("municipalicy",binding.munEditText.getText().toString());
                    crudFarmer.update(hashMap).addOnSuccessListener(unused -> {
                        Toasty.success(FarmerRegisterActivity.this, "Successfully update your farmer profile !", Toasty.LENGTH_SHORT,true).show();
                        binding.districtEditText.setText("");
                        binding.mblNumEditText.setText("");
                        binding.munEditText.setText("");
                        Intent intent = new Intent(FarmerRegisterActivity.this, ProductDashboard.class);
                        startActivity(intent);
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(FarmerRegisterActivity.this, ""+e.getMessage(), Toasty.LENGTH_SHORT,true).show();
                        }
                    });

                }

            }
        });

    }


}