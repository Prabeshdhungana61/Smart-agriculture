package com.thebigoceaan.smartagriculture.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.models.Order;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    ArrayList<Order> list = new ArrayList<>();
    private RecyclerViewClickListener listener;


    public OrderAdapter(Context context, ArrayList<Order> list, RecyclerViewClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;

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

    public class OrderVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView productTitle, sellerEmail, orderDate;
        ImageButton deleteButton;
        public OrderVH(@NonNull View itemView) {
            super(itemView);

            productTitle = itemView.findViewById(R.id.item_order_productTitle);
            sellerEmail = itemView.findViewById(R.id.item_order_sellerEmail);
            orderDate = itemView.findViewById(R.id.item_order_date);
            deleteButton = itemView.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getBindingAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }

}
