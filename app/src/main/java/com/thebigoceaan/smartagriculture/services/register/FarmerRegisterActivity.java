package com.thebigoceaan.smartagriculture.services.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.ActivityFarmerRegisterBinding;
import com.thebigoceaan.smartagriculture.models.Farmer;
import com.thebigoceaan.smartagriculture.models.Location;
import com.thebigoceaan.smartagriculture.models.News;
import com.thebigoceaan.smartagriculture.models.Product;
import com.thebigoceaan.smartagriculture.services.products.ProductDashboard;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class FarmerRegisterActivity extends AppCompatActivity {
    ActivityFarmerRegisterBinding binding;
    private FirebaseAuth auth;
    private CrudFarmer crudFarmer = new CrudFarmer();
    ArrayList<String> district = new ArrayList<String>();
    ArrayList<String> vdc = new ArrayList<String>();
    JSONObject jsonObject;
    JSONArray jsonArray;
    String getDistrict,getVDC;
    String json_string;


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
        JSONObject jsonObject;

        //district json
        json_string= loadJSONFromAsset();
        {
            try {
                jsonObject =new JSONObject(json_string);
                JSONArray jsonArray =jsonObject.getJSONArray("location");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    getDistrict= jObj.getString("District");
                    getVDC = jObj.getString("VDC_name");
                    if(!district.contains(getDistrict) ){
                        district.add(getDistrict);
                        jsonArray.getJSONObject(i);
                    }
                    vdc.add(getVDC);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //for dropdown
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, district);
        ArrayAdapter<String> vdcAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vdc);
        districtAdapter.setDropDownViewResource(R.layout.item_dropdown);
        binding.districtEditText.setAdapter(districtAdapter);
        binding.munEditText.setAdapter(vdcAdapter);

        //get data for edit or update
        Farmer farmer_edit= (Farmer) this.getIntent().getSerializableExtra("EDITFARMER");

        if(farmer_edit!=null){
            binding.btnRegFarmer.setText("UPDATE FARMER PROFILE");
            binding.districtEditText.setText(farmer_edit.getDistrict());
            binding.munEditText.setText(farmer_edit.getMunicipality());
            binding.mblNumEditText.setText(farmer_edit.getMobile());
            //for dropdown
            ArrayAdapter<String> districtAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, district);
            ArrayAdapter<String> vdcAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vdc);
            districtAdapter.setDropDownViewResource(R.layout.item_dropdown);
            binding.districtEditText.setAdapter(districtAdapter2);
            binding.munEditText.setAdapter(vdcAdapter2);

        }
        else{
            binding.btnRegFarmer.setText(R.string.reg_farmer);
        }

        binding.btnRegFarmer.setOnClickListener(view -> {
            if(farmer_edit==null) {
                Farmer farmer = new Farmer();
                farmer.setDistrict(binding.districtEditText.getText().toString().trim());
                farmer.setMunicipality(binding.munEditText.getText().toString().trim());
                farmer.setMobile(binding.mblNumEditText.getText().toString().trim());
                farmer.setUserid(auth.getCurrentUser().getUid());
                if (auth.getCurrentUser() != null) {
                    CrudFarmer crud = new CrudFarmer();
                    crud.add(farmer).addOnSuccessListener(unused -> {
                        binding.munEditText.setText("");
                        binding.districtEditText.setText("");
                        binding.mblNumEditText.setText("");
                        Toasty.success(FarmerRegisterActivity.this, "Successfully registered as farmer", Toast.LENGTH_SHORT, true).show();
                    }).addOnFailureListener(e -> Toasty.error(FarmerRegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT, true).show());

                } else {
                    Toasty.warning(FarmerRegisterActivity.this, "Kindly Login first to register as a farmer", Toast.LENGTH_SHORT, true).show();
                }
            }
            else{
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("district",binding.districtEditText.getText().toString());
                hashMap.put("mobile",binding.mblNumEditText.getText().toString());
                hashMap.put("municipality",binding.munEditText.getText().toString());
                crudFarmer.update(hashMap).addOnSuccessListener(unused -> {
                    Toasty.success(FarmerRegisterActivity.this, "Successfully update your farmer profile !", Toasty.LENGTH_SHORT,true).show();
                    binding.districtEditText.setText("");
                    binding.mblNumEditText.setText("");
                    binding.munEditText.setText("");
                    Intent intent = new Intent(FarmerRegisterActivity.this, ProductDashboard.class);
                    startActivity(intent);
                }).addOnFailureListener(e -> Toasty.error(FarmerRegisterActivity.this, ""+e.getMessage(), Toasty.LENGTH_SHORT,true).show());
            }

        });

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("district.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}