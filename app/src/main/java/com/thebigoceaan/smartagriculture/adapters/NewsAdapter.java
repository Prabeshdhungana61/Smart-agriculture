package com.thebigoceaan.smartagriculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.dashboard.AddNewsActivity;
import com.thebigoceaan.smartagriculture.dashboard.CrudNews;
import com.thebigoceaan.smartagriculture.models.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    ArrayList<News> list = new ArrayList<>();
    CrudNews crud = new CrudNews();

    public NewsAdapter(Context context) {
        this.context = context;
    }
    public void setItem(ArrayList<News> news){
        list.addAll(news);
    }
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);
        return new NewsVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        NewsVH vh = (NewsVH) holder;
        News news = list.get(position);
        vh.news_title.setText(news.getNews_title());
        vh.summary.setText(news.getNews_summary());
        vh.menuText.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context,vh.menuText);
            popupMenu.inflate(R.menu.task);
            popupMenu.setOnMenuItemClickListener(item ->
            {
                switch(item.getItemId()){
                    case R.id.action_edit:
                        Intent intent = new Intent(context, AddNewsActivity.class);
                        intent.putExtra("EDIT", news);
                        context.startActivity(intent);
                        break;

                    case R.id.action_remove:
                        crud.remove(news.getKey()).addOnSuccessListener(suc -> {
                            Toast.makeText(context, "upcoming ipo removed successfully",
                                    Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText
                                    (context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

}
class NewsVH  extends RecyclerView.ViewHolder {
    public TextView news_title,summary,date,menuText;
    public NewsVH(@NonNull @NotNull View itemView) {
        super(itemView);
        news_title = itemView.findViewById(R.id.news_title_text_view);
        summary = itemView.findViewById(R.id.news_summary_text_view);
        date= itemView.findViewById(R.id.news_date_text_view);
        menuText=itemView.findViewById(R.id.menuText2);

    }
}
