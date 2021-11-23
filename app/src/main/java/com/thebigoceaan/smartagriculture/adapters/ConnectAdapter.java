package com.thebigoceaan.smartagriculture.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.models.Connect;
import org.jetbrains.annotations.NotNull;


public class ConnectAdapter extends FirebaseRecyclerAdapter<Connect, ConnectAdapter.ConnectVH> {


    public ConnectAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Connect> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ConnectVH holder, int position, @NonNull @NotNull Connect model) {
            holder.username.setText(model.getUsername());
            holder.message.setText(model.getMessage());
    }

    @NonNull
    @NotNull
    @Override
    public ConnectVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_connect,parent,false);
        return new ConnectVH(view);
    }

    public class ConnectVH extends RecyclerView.ViewHolder {
        TextView username,message;
        public ConnectVH(@NonNull @NotNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.msg_sent_user_text_view);
            message=itemView.findViewById(R.id.msg_sent_connect_details_text);
        }
    }

}
