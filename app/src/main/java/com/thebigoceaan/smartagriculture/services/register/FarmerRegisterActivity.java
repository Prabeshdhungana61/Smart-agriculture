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
        binding= ActivityFarmerRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4fb424"));
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends

        //get instance
        auth = FirebaseAuth.getInstance();

        //hide linear layout
        binding.farmerProfileLinearLayout.setVisibility(View.GONE);

        Farmer farmer_edit= (Farmer) getIntent().getSerializableExtra("EDIT");

        if(farmer_edit!=null){
            binding.btnRegFarmer.setText("UPDATE FARMER PROFILE");
            binding.districtEditText.setText(farmer_edit.getDistrict());
            binding.munEditText.setText(farmer_edit.getMunicipality());
            binding.mblNumEditText.setText(farmer_edit.getMobile());
        }
        else{
            binding.btnRegFarmer.setText("ADD FARMER PROFILE");
        }

        Farmer farmer = new Farmer();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(farmer!=null){
            validateInput();
        }
        else{
            binding.farmerProfileLinearLayout.setVisibility(View.VISIBLE);
            Glide.with(this).load(user.getPhotoUrl().toString()).into(binding.profileImageFarmer);
            binding.usernameTextFarmer.setText(user.getDisplayName());
            binding.mblNumEditText.setText(farmer.getMobile());
            binding.addressTextFarmer.setText(farmer.getMunicipality() + farmer.getDistrict());

        }
    }

    public void addFarmer(){
        Farmer farmer_edit= (Farmer) getIntent().getSerializableExtra("EDIT");
        binding.btnRegFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(farmer_edit==null) {
                    Farmer farmer = new Farmer(binding.mblNumEditText.getText().toString().trim()
                            , binding.districtEditText.getText().toString().trim(), binding.munEditText.getText().toString());
                    if (auth.getCurrentUser() != null) {
                        CrudFarmer crud = new CrudFarmer();
                        if (!validateInput()) {
                            crud.addFarmer(farmer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    binding.mblNumEditText.setText("");
                                    binding.districtEditText.setText("");
                                    binding.munEditText.setText("");
                                    Toasty.success(FarmerRegisterActivity.this, "Successfully Registered as a Farmer", Toast.LENGTH_SHORT, true).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toasty.error(FarmerRegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                }
                            });
                        } else {
                            return;
                        }

                    }
                }
                else{
                    HashMap<String, Object> hashMap = new HashMap<>();
                    CrudFarmer crud = new CrudFarmer();
                    hashMap.put("district",binding.districtEditText.getText().toString().toString().trim());
                    hashMap.put("mobile",binding.mblNumEditText.getText().toString().trim());
                    hashMap.put("municipality",binding.munEditText.getText().toString().trim());
                    crud.update(farmer_edit.getKey(),hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            binding.mblNumEditText.setText("");
                            binding.munEditText.setText("");
                            binding.districtEditText.setText("");
                            Toasty.success(FarmerRegisterActivity.this, "Your profile updated successfully", Toast.LENGTH_SHORT,true).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(FarmerRegisterActivity.this,""+e.getMessage(),Toasty.LENGTH_SHORT,true).show();
                        }
                    });

                }

            }
        });
    }
    public boolean validateInput(){
        if(binding.districtEditText.getText().toString().trim().equals("") && binding.munEditText.getText().toString().trim().equals("")
        && binding.mblNumEditText.getText().toString().trim().equals("")){
            Toasty.warning(this, "Kindly input text first !", Toast.LENGTH_SHORT,true).show();
            return false;
        }
        return true;
    }
}