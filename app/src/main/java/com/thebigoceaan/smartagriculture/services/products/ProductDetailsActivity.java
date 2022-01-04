package com.thebigoceaan.smartagriculture.services.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.ActivityProductDetailsBinding;
import com.thebigoceaan.smartagriculture.models.Order;
import com.thebigoceaan.smartagriculture.models.Product;
import com.thebigoceaan.smartagriculture.services.order.CrudOrder;

import org.jetbrains.annotations.NotNull;

import es.dmoral.toasty.Toasty;

public class ProductDetailsActivity extends AppCompatActivity {
    ActivityProductDetailsBinding binding;
    String title, sellerProfile,sellerMobile,sellerEmail, price, description, image,totalProductStock;
    CrudOrder crud;
    private FirebaseAuth auth;
    Order order;
    Dialog dialog;



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

        auth = FirebaseAuth.getInstance();

        dialog = new Dialog(this);

        binding.orderByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.dialog_stock);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageButton req_button = dialog.findViewById(R.id.send_req_button);
                EditText yourStock = dialog.findViewById(R.id.your_stock_edit_text);
                dialog.show();
                req_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String buyerEmail = auth.getCurrentUser().getEmail();
                        String buyerName =  auth.getCurrentUser().getDisplayName();
                        String buyerProfile = auth.getCurrentUser().getPhotoUrl().toString();
                        String myStock = yourStock.getText().toString();
                        int orderStock = Integer.parseInt(myStock);
                        int perUnitPrice = Integer.parseInt(price);
                        int orderPrice = orderStock * perUnitPrice;
                        order = new Order(sellerEmail,buyerEmail,buyerName,buyerProfile,title,myStock,""+orderPrice,false);
                        crud = new CrudOrder();
                        try {
                            crud.add(order).addOnSuccessListener(unused -> {
                                yourStock.setText("");
                                dialog.dismiss();
                                Toasty.success(ProductDetailsActivity.this, "Successfully sent order to the farmer !", Toasty.LENGTH_SHORT, true).show();
                            }).addOnFailureListener(e -> {
                                        dialog.dismiss();
                                        Toasty.error(ProductDetailsActivity.this, "" + e.getMessage(), Toasty.LENGTH_SHORT, true).show();

                                    });
                        }
                        catch (Exception e){
                            Toast.makeText(ProductDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("SellerEmail") && getIntent().hasExtra("SellerMobile")
                && getIntent().hasExtra("TotalProductStock")
                && getIntent().hasExtra("SellerProfile") && getIntent().hasExtra("TitleProductText")
                && getIntent().hasExtra("ProductPriceText")
                && getIntent().hasExtra("ProductImage") && getIntent().hasExtra("ProductDescText")
        ) {
            //Getting Data from Intent
            title = getIntent().getStringExtra("TitleProductText");
            price = getIntent().getStringExtra("ProductPriceText");
            description = getIntent().getStringExtra("ProductDescText");
            image = getIntent().getStringExtra("ProductImage");
            sellerProfile=getIntent().getStringExtra("SellerProfile");
            sellerEmail=getIntent().getStringExtra("SellerEmail");
            sellerMobile=getIntent().getStringExtra("SellerMobile");
            totalProductStock = getIntent().getStringExtra("TotalProductStock");

            //Setting Intent Data
            binding.productTitleDetails.setText(title);
            binding.productDetailsPrice.setText(price + "Rs.");
            binding.productDescriptionDetails.setText(description);
            binding.productDetailsTotalStock.setText(totalProductStock);

            Glide.with(this).load(image).placeholder(R.drawable.ic_image).into(binding.imageView);
            Glide.with(this).load(sellerProfile).placeholder(R.drawable.ic_image).into(binding.sellerProfileImage);
            binding.sendEmailTextView.setText(sellerEmail);
            binding.sellerMobileTextView.setText(sellerMobile);

        } else {
            Toasty.error(this, "No data Found !", Toasty.LENGTH_SHORT, true).show();
        }
    }

}