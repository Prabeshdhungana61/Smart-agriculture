package com.thebigoceaan.smartagriculture.services.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.ActivityFarmerRegisterBinding;
import com.thebigoceaan.smartagriculture.models.Farmer;
import com.thebigoceaan.smartagriculture.models.News;

import org.jetbrains.annotations.NotNull;

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

        Farmer farmer_edit= (Farmer) getIntent().getSerializableExtra("EDIT");

        binding.btnRegFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Farmer farmer = new Farmer(binding.mblNumEditText.getText().toString().trim()
                        ,binding.districtEditText.getText().toString().trim(),binding.munEditText.getText().toString());
                if(farmer_edit!=null) {
                    CrudFarmer crud = new CrudFarmer();
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
                }

            }
        });
    }
}