package com.thebigoceaan.smartagriculture.services.products;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.ActivityProductDetailsBinding;

import es.dmoral.toasty.Toasty;

public class ProductDetailsActivity extends AppCompatActivity {
    ActivityProductDetailsBinding binding;
    String title, stock, price, description, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getAndSetIntentData();

        // Set App bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4fb424"));
        getSupportActionBar().setTitle(title);
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("TitleProductText") &&
                getIntent().hasExtra("StockProductText") && getIntent().hasExtra("ProductPriceText")
                && getIntent().hasExtra("ProductImage") && getIntent().hasExtra("ProductDescText")
        ) {
            //Getting Data from Intent
            title = getIntent().getStringExtra("TitleProductText");
            stock = getIntent().getStringExtra("StockProductText");
            price = getIntent().getStringExtra("ProductPriceText");
            description = getIntent().getStringExtra("ProductDescText");
            image = getIntent().getStringExtra("ProductImage");


            //Setting Intent Data
            binding.productTitleDetails.setText(title);
            binding.productStockDetails.setText(stock+ "K.G. ");
            binding.productDetailsPrice.setText(price + "Rs.");
            binding.productDescriptionDetails.setText(description);
            Glide.with(this).load(image).placeholder(R.drawable.ic_image).into(binding.imageView);
        } else {
            Toasty.error(this, "No data Found !", Toasty.LENGTH_SHORT, true).show();
        }
    }
}