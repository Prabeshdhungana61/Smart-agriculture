package com.thebigoceaan.smartagriculture.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.models.News;
import com.thebigoceaan.smartagriculture.models.Order;
import com.thebigoceaan.smartagriculture.services.order.CrudOrder;

import java.util.ArrayList;

public class FarmerProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<Order> list = new ArrayList<>();
    CrudOrder order = new CrudOrder();

    public FarmerProfileAdapter(ArrayList<Order> list) {
        this.list = list;
    }
    public void setItem(ArrayList<Order> order){
        list.addAll(order);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_farmer_profile,parent,false);
        return new FarmerProfileVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FarmerProfileVH vh = (FarmerProfileVH) holder;
        Order order = list.get(position);
        vh.id.setText("1");
        vh.title.setText(order.getProductTitle());
        vh.buyerEmail.setText(order.getBuyerEmail());
        vh.buyerStock.setText(order.getStockTotal());
        vh.totalBuyerPrice.setText(order.getOrderPrice());
        vh.buyerName.setText(order.getBuyerName());
        vh.orderCompleted.setText(!order.isCompleted() ? "NOT COMPLETED ": "COMPLETED");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FarmerProfileVH extends RecyclerView.ViewHolder {
        TextView id, title, buyerEmail, buyerName, buyerStock,totalBuyerPrice, orderCompleted;
        public FarmerProfileVH(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_order_id);
            title = itemView.findViewById(R.id.item_order_title);
            buyerEmail = itemView.findViewById(R.id.item_order_bemail);
            buyerName = itemView.findViewById(R.id.item_order_bname);
            buyerStock = itemView.findViewById(R.id.item_order_bstock);
            totalBuyerPrice = itemView.findViewById(R.id.item_order_price);
            orderCompleted = itemView.findViewById(R.id.item_order_done);

        }
    }
}
