package com.thebigoceaan.smartagriculture.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.models.Product;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    ArrayList<Product> list = new ArrayList<>();

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        return new ProductVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ProductVH vh = (ProductVH) holder;
        Product product = list.get(position);
        Glide.with(context).load(product.getProductImage()).fitCenter().centerCrop().into(vh.productImage);
        vh.title.setText(product.getProductTitle());
        vh.price.setText(product.getProductPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductVH  extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView productImage;
        public ProductVH(@NonNull @com.google.firebase.database.annotations.NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title_text);
            price = itemView.findViewById(R.id.item_price_text);
            productImage=itemView.findViewById(R.id.item_product_img);
        }
    }
}
