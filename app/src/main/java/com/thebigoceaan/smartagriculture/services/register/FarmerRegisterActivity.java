package com.thebigoceaan.smartagriculture.services.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class FarmerRegisterActivity extends AppCompatActivity {
    ActivityFarmerRegisterBinding binding;
    private FirebaseAuth auth;

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

        binding.btnRegFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Farmer farmer = new Farmer();
                farmer.setDistrict(binding.districtEditText.getText().toString().trim());
                farmer.setMunicipality(binding.munEditText.getText().toString().trim());
                farmer.setMobile(binding.mblNumEditText.getText().toString().trim());
                if(auth.getCurrentUser()!=null) {
                    CrudFarmer crud = new CrudFarmer();
                    crud.add(farmer).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            binding.munEditText.setText("");
                            binding.districtEditText.setText("");
                            binding.mblNumEditText.setText("");
                            Toasty.success(FarmerRegisterActivity.this, "Successfully registered as farmer", Toast.LENGTH_SHORT,true).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toasty.error(FarmerRegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT,true).show();
                        }
                    });

                }
                else{
                    Toasty.warning(FarmerRegisterActivity.this, "Kindly Login first to register as a farmer", Toast.LENGTH_SHORT,true).show();
                }

            }
        });

    }


}