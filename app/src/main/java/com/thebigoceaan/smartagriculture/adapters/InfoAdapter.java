package com.thebigoceaan.smartagriculture.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.dashboard.info.AddInfoActivity;
import com.thebigoceaan.smartagriculture.dashboard.info.CrudInfo;
import com.thebigoceaan.smartagriculture.dashboard.info.ViewInfoActivity;
import com.thebigoceaan.smartagriculture.models.Info;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    ArrayList<Info> list = new ArrayList<>();
    CrudInfo crud = new CrudInfo();
    Dialog dialog;
    StorageReference mStorageReference = FirebaseStorage.getInstance().getReference(Info.class.getSimpleName());

    public InfoAdapter(Context context) {
        this.context = context;
    }
    public void setItem(ArrayList<Info> info){
        list.addAll(info);
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info,parent,false);
        return new InfoVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        dialog = new Dialog(context);
        InfoVH vh = (InfoVH) holder;
        Info info = list.get(position);
        vh.info_title.setText(info.getInfoTitle());
        vh.details.setText(info.getInfoDetails());
        Glide.with(context).load(info.getInfoImage()).fitCenter().centerCrop().into(vh.imageInfo);
        vh.menuText.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context,vh.menuText);
            popupMenu.inflate(R.menu.task);
            popupMenu.setOnMenuItemClickListener(item ->
            {
                switch(item.getItemId()){
                    case R.id.action_edit:
                        Intent intent = new Intent(context, AddInfoActivity.class);
                        intent.putExtra("EDIT", info);
                        context.startActivity(intent);
                        break;

                    case R.id.action_remove:
                        dialog.setContentView(R.layout.dialog_confirmation);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button yesBtn = dialog.findViewById(R.id.btn_yes_confirm);
                        Button noBtn = dialog.findViewById(R.id.btn_no_confirm);
                        dialog.show();
                        yesBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                crud.remove(info.getKey()).addOnSuccessListener(suc -> {
                                    Intent intent2 = new Intent(context, ViewInfoActivity.class);
                                    context.startActivity(intent2);
                                    Toast.makeText(context, "agro information removed successfully",
                                            Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(e -> {
                                    Toast.makeText
                                            (context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                        noBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        break;

                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoVH  extends RecyclerView.ViewHolder {
        public TextView info_title, details, menuText;
        public ImageView imageInfo;

        public InfoVH(@NonNull @com.google.firebase.database.annotations.NotNull View itemView) {
            super(itemView);
            info_title = itemView.findViewById(R.id.title_for_info);
            details = itemView.findViewById(R.id.details_for_info);
            imageInfo = itemView.findViewById(R.id.image_for_agro_info);
            menuText = itemView.findViewById(R.id.menu_text_info);

        }
    }
}
