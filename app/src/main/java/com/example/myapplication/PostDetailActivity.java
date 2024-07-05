package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ImageView postImage = findViewById(R.id.post_detail_image);
        TextView postTitle = findViewById(R.id.post_detail_title);
        TextView postContent = findViewById(R.id.post_detail_content);

        Intent intent = getIntent();
        int imageResId = intent.getIntExtra("imageResId", 0);
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        postImage.setImageResource(imageResId);
        postTitle.setText(title);
        postContent.setText(content);
    }
}
