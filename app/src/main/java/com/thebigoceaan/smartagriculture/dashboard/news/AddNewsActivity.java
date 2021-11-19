package com.thebigoceaan.smartagriculture.dashboard.news;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.databinding.ActivityAddNewsBinding;
import com.thebigoceaan.smartagriculture.models.News;
import java.util.HashMap;

public class AddNewsActivity extends AppCompatActivity {
    ActivityAddNewsBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get reference of CrudNews activity
        CrudNews crud = new CrudNews();
        //for dropdown buttons
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.source_list, R.layout.item_dropdown);
        adapter.setDropDownViewResource(R.layout.item_dropdown);
        binding.newsSourceEditText.setAdapter(adapter);
        //action bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4fb424"));
        actionBar.setBackgroundDrawable(colorDrawable); //action bar ends
        //get instance
        auth = FirebaseAuth.getInstance();

        //for updating the news
        News news_edit= (News) getIntent().getSerializableExtra("EDIT");
        if(news_edit!=null){
            binding.btnNewsAdd.setText(R.string.update_news);
            binding.newsSourceEditText.setText(news_edit.getNews_source());
            binding.newsLinkEditText.setText(news_edit.getNews_link());
            binding.newsSummaryEditText.setText(news_edit.getNews_summary());
            binding.newsTitleEditText.setText(news_edit.getNews_title());
            binding.newsDateEditText.setText(news_edit.getNews_date());

        }
        else{
            binding.btnNewsAdd.setText(R.string.add_news);
        }

        //for news add button pressed
        binding.btnNewsAdd.setOnClickListener(view -> {
            News news = new News(binding.newsTitleEditText.getText().toString(),
                    binding.newsSummaryEditText.getText().toString().trim(),
                    binding.newsSourceEditText.getText().toString().trim(),
                    binding.newsLinkEditText.getText().toString(),
                    binding.newsDateEditText.getText().toString());
            if(news_edit==null) {
                if (validateNewsTitle() && validateNewsSource() && validateNewsLink() && validateNewsDate()&& validateNewsSummary() ){
                    return;
                }
                crud.add(news).addOnSuccessListener(suc -> {
                    binding.newsTitleEditText.setText("");
                    binding.newsLinkEditText.setText("");
                    binding.newsSummaryEditText.setText("");
                    binding.newsDateEditText.setText("");

                    Intent intent = new Intent (getApplicationContext(), ViewNewsActivity.class);
                    startActivity(intent);

                    Toast.makeText(AddNewsActivity.this, "News inserted successfully",
                            Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText
                        (AddNewsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
            else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("news_title", binding.newsTitleEditText.getText().toString());
                hashMap.put("news_source", binding.newsSourceEditText.getText().toString());
                hashMap.put("news_link", binding.newsLinkEditText.getText().toString());
                hashMap.put("news_summary", binding.newsSummaryEditText.getText().toString());
                hashMap.put("news_date",binding.newsDateEditText.getText().toString());


                crud.update(news_edit.getKey(), hashMap).addOnSuccessListener(suc -> {
                    Intent intent = new Intent (getApplicationContext(), ViewNewsActivity.class);
                    startActivity(intent);
                    Toast.makeText(AddNewsActivity.this, "news updated successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> Toast.makeText
                        (AddNewsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

    }

    private boolean validateNewsTitle(){
        if(binding.newsTitleEditText.getText().toString().length()==0){
            binding.newsTitleEditText.setError("You must have to write news title...");
        }
        return true;
    }
    private boolean validateNewsSource(){
        if(binding.newsSourceEditText.getText().toString().length()==0){
            binding.newsSourceEditText.setError("You must have to write news source...");
        }
        return true;
    }
    private boolean validateNewsLink(){
        if(binding.newsLinkEditText.getText().toString().length()==0){
            binding.newsLinkEditText.setError("You must have to put news Link...");
        }
        return true;
    }
    private boolean validateNewsSummary(){
        if(binding.newsSummaryEditText.getText().toString().length()==0){
            binding.newsSummaryEditText.setError("You must have to put news title...");
        }
        return true;
    }
    private boolean validateNewsDate(){
        if(binding.newsDateEditText.getText().toString().length()==0){
            binding.newsDateEditText.setError("You must have to put news title...");
        }
        return true;
    }



}