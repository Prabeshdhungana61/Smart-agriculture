package com.thebigoceaan.smartagriculture.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.models.Order;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    ArrayList<Order> list = new ArrayList<>();

    public OrderAdapter(Context context, ArrayList<Order> list){
        this.context = context;
        this.list = list;
    }

    public void setItem(ArrayList<Order> order){
        list.addAll(order);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderVH vh = (OrderVH) holder;
        Order order = list.get(position);
        vh.orderDate.setText(order.getOrderDate());
        vh.sellerEmail.setText(order.getSellerEmail());
        vh.productTitle.setText(order.getProductTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderVH extends RecyclerView.ViewHolder {
        TextView productTitle, sellerEmail, orderDate;
        public OrderVH(@NonNull View itemView) {
            super(itemView);

            productTitle = itemView.findViewById(R.id.item_order_productTitle);
            sellerEmail = itemView.findViewById(R.id.item_order_sellerEmail);
            orderDate = itemView.findViewById(R.id.item_order_date);
        }
    }


}
