package com.thebigoceaan.smartagriculture.services.register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.ActivityTestBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class TestActivity extends AppCompatActivity {
    ActivityTestBinding binding;
    String json_string;
    String getDistrict;
    String getProvince,getMunicipality;
    JSONObject jsonObject,jsonObject2;
    ArrayList<String> provinceArray = new ArrayList<>();
    ArrayList<String> munArray = new ArrayList<>();
    ArrayList<String> districtArray = new ArrayList<>();
    int selectedId;
    ArrayAdapter<String> provinceAdapter,districtAdapter,municipalityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //JSON TO JAVA
        json_string= loadJSONFromAsset();
        {
            try {
                jsonObject =new JSONObject(json_string);
                JSONArray jsonArray =jsonObject.getJSONArray("location");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    for (int j=0;j< jObj.length();j++){
                        getProvince = jObj.names().getString(j);
                        provinceArray.add(getProvince);
                        binding.testProvince.setOnItemClickListener((adapterView, view, position, l) -> {
                            selectedId = position;
                            for(selectedId=0;selectedId<jObj.length();selectedId++){
                                try {
                                    for (int k=0;k<jObj.getJSONObject(binding.testProvince.getText().toString()).names().length();k++){
                                        getDistrict = jObj.getJSONObject(binding.testProvince.getText().toString().trim()).names().getString(k);
                                        if (!districtArray.contains(getDistrict)) {
                                            districtArray.add(getDistrict);
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        binding.testDistrict.setOnItemClickListener((adapterView, view, i1, l) -> {
                            selectedId = i1;
                            for(selectedId=0;selectedId<districtArray.size();selectedId++){
                                {
                                    try {
                                        JSONArray jsonArray1 = jObj.getJSONObject(binding.testProvince.getText().toString()).getJSONArray(binding.testDistrict.getText().toString());
                                        for (int m=0;m<jsonArray1.length();m++){
                                            getMunicipality = jsonArray1.getString(m);
                                            if (!munArray.contains(getMunicipality)) {
                                                munArray.add(getMunicipality);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

        //for dropdown
        provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinceArray);
        provinceAdapter.setDropDownViewResource(R.layout.item_dropdown);
        binding.testProvince.setAdapter(provinceAdapter);
        //for dropdown
        districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districtArray);
        provinceAdapter.setDropDownViewResource(R.layout.item_dropdown);
        binding.testDistrict.setAdapter(districtAdapter);
        //for dropdown
        municipalityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, munArray);
        municipalityAdapter.setDropDownViewResource(R.layout.item_dropdown);
        binding.testMun.setAdapter(municipalityAdapter);




    }
    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is1 = getAssets().open("location.json");
            int size = is1.available();
            byte[] buffer = new byte[size];
            is1.read(buffer);
            is1.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}