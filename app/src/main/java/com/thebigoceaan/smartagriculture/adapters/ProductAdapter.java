package com.thebigoceaan.smartagriculture.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.models.Product;
import com.thebigoceaan.smartagriculture.services.products.CrudProduct;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    ArrayList<Product> list = new ArrayList<>();
    CrudProduct crud = new CrudProduct();

    public ProductAdapter(Context context) {
        this.context = context;
    }
    public void setItem(ArrayList<Product> product){
        list.addAll(product);
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info,parent,false);
        return new ProductVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductVH  extends RecyclerView.ViewHolder {

        public ProductVH(@NonNull @com.google.firebase.database.annotations.NotNull View itemView) {
            super(itemView);

        }
    }
}
