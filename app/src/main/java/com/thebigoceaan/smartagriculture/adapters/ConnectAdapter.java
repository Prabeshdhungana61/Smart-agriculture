package com.thebigoceaan.smartagriculture.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.models.Connect;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class ConnectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    ArrayList<Connect> list = new ArrayList<>();


    public ConnectAdapter(Context context) {
        this.context = context;
    }
    public void setItem(ArrayList<Connect> connect){
        list.addAll(connect);
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_connect,parent,false);
        return new ConnectVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ConnectVH vh = (ConnectVH) holder;
        Connect connect = list.get(position);
        vh.username.setText(connect.getUsername());
        vh.msg.setText(connect.getMessage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ConnectVH  extends RecyclerView.ViewHolder {
        public TextView username, msg;

        public ConnectVH(@NonNull @com.google.firebase.database.annotations.NotNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.msg_sent_user_text_view);
            msg = itemView.findViewById(R.id.msg_sent_connect_details_text);

        }
    }

}
